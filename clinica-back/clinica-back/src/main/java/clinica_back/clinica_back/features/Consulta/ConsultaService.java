package clinica_back.clinica_back.features.Consulta;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import clinica_back.clinica_back.features.Consulta.Disponibilidade.DisponibilidadeService;
import org.springframework.stereotype.Service;


import clinica_back.clinica_back.features.Auditoria.AcaoAuditoriaEnum;
import clinica_back.clinica_back.features.Auditoria.LogAuditoriaService;
import clinica_back.clinica_back.features.Consulta.AgendaPadrao.AgendaPadrao;
import clinica_back.clinica_back.features.Consulta.AgendaPadrao.AgendaPadraoRepository;
import clinica_back.clinica_back.features.Consulta.AgendaPadrao.HorarioBloqueado.HorarioBloqueadoRepository;
import clinica_back.clinica_back.features.Consulta.DTOs.ConsultaRequestDTO;
import clinica_back.clinica_back.features.Consulta.DTOs.ConsultaResponseDTO;
import clinica_back.clinica_back.features.Consulta.DTOs.ConsultaSemProntuarioResponseDTO;
import clinica_back.clinica_back.features.Usuario.Medico.Medico;
import clinica_back.clinica_back.features.Usuario.Medico.MedicoRepository;
import clinica_back.clinica_back.features.Usuario.Paciente.Paciente;
import clinica_back.clinica_back.features.Usuario.Paciente.PacienteRepository;
import clinica_back.clinica_back.shared.exceptions.RecursoNaoEncontradoException;
import clinica_back.clinica_back.shared.exceptions.RegraNegocioException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultaService {

        private final LogAuditoriaService logAuditoriaService;
        private final ConsultaRepository consultaRepository;
        private final PacienteRepository pacienteRepository;
        private final MedicoRepository medicoRepository;
        private final AgendaPadraoRepository agendaRepository;
        private final HorarioBloqueadoRepository bloqueioRepository;
        private final DisponibilidadeService disponibilidadeService;


        public Consulta cadastrar(ConsultaRequestDTO dto) {

                Paciente paciente = pacienteRepository.findById(dto.getIdPaciente())
                                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado"));

                Medico medico = medicoRepository.findById(dto.getIdMedico())
                                .orElseThrow(() -> new RecursoNaoEncontradoException("Médico não encontrado"));

                DayOfWeek diaSemana = dto.getDataConsulta().getDayOfWeek();

                AgendaPadrao agenda = agendaRepository
                                .findByMedicoAndDiaSemana(medico, diaSemana)
                                .orElseThrow(() -> new RecursoNaoEncontradoException("O médico não atende nesse dia"));

                if (dto.getHoraConsulta().isBefore(agenda.getHoraInicio())
                                || dto.getHoraConsulta().isAfter(agenda.getHoraFim())) {
                        throw new RegraNegocioException("Horário fora da agenda do médico");
                }

                long minutosEntre = Duration.between(
                                agenda.getHoraInicio(),
                                dto.getHoraConsulta()).toMinutes();

                if (minutosEntre % agenda.getIntervaloMinutos() != 0) {
                        throw new RegraNegocioException(
                                        "Horário inválido para o intervalo da agenda.");
                }

                boolean horarioBloqueado = bloqueioRepository
                                .existsByMedicoAndDataAndHoraInicioLessThanEqualAndHoraFimGreaterThanEqual(
                                                medico,
                                                dto.getDataConsulta(),
                                                dto.getHoraConsulta(),
                                                dto.getHoraConsulta());

                if (horarioBloqueado) {
                        throw new RegraNegocioException("Esse horário está bloqueado");
                }

                boolean consultaExistente = consultaRepository
                                .existsByMedicoAndDataConsultaAndHoraConsulta(
                                                medico,
                                                dto.getDataConsulta(),
                                                dto.getHoraConsulta());

                if (consultaExistente) {
                        throw new RegraNegocioException(
                                        "Já existe uma consulta nesse horário");
                }
                boolean horarioLivre = disponibilidadeService
                        .listarHorariosDisponiveis(
                                dto.getIdMedico(),
                                dto.getDataConsulta())
                        .stream()
                        .anyMatch(h -> h.getHora().equals(dto.getHoraConsulta()));

                if (!horarioLivre) {
                        throw new RegraNegocioException(
                                "Esse horário não está disponível.");
                }

                Consulta consulta = new Consulta();

                consulta.setPaciente(paciente);
                consulta.setMedico(medico);
                consulta.setDataConsulta(dto.getDataConsulta());
                consulta.setHoraConsulta(dto.getHoraConsulta());
                consulta.setStatusConsulta(StatusConsulta.AGENDADO);

                // 1. Salva primeiro no banco de dados para gerar o ID da consulta
                Consulta consultaSalva = consultaRepository.save(consulta);

                // 2. Agora que consultaSalva possui o ID populado, passamos para a auditoria
                logAuditoriaService.registrar(
                        AcaoAuditoriaEnum.CREATE,
                        "CONSULTA",
                        consultaSalva.getIdConsulta(), // <-- Agora não é mais nulo!
                        "Consulta de Paciente " + consultaSalva.getPaciente().getNome() + " "
                                + consultaSalva.getPaciente().getSobrenome() + " Com o médico: "
                                + consultaSalva.getMedico().getNome() + " "
                                + consultaSalva.getMedico().getSobrenome()
                );

                // 3. Retorna a consulta que já foi salva
                return consultaSalva;
        }

        public Consulta statusConsulta(Long idConsulta) {

                Consulta consulta = consultaRepository.findById(idConsulta)
                                .orElseThrow(() -> new RecursoNaoEncontradoException("Consulta não encontrada"));

                if (consulta.getStatusConsulta() == StatusConsulta.CANCELADO) {
                        throw new RegraNegocioException(
                                        "Consulta cancelada não pode ser realizada.");
                }

                if (consulta.getStatusConsulta() == StatusConsulta.REALIZADO) {
                        throw new RegraNegocioException(
                                        "Consulta já foi realizada.");
                }

                consulta.setStatusConsulta(StatusConsulta.REALIZADO);

                return consultaRepository.save(consulta);
        }

        private ConsultaResponseDTO converterParaDTO(Consulta consulta) {
                ConsultaResponseDTO dto = new ConsultaResponseDTO();

                dto.setIdConsulta(consulta.getIdConsulta());
                dto.setNomePaciente(consulta.getPaciente().getNome());
                dto.setNomeMedico(consulta.getMedico().getNome());
                dto.setDataConsulta(consulta.getDataConsulta());
                dto.setHoraConsulta(consulta.getHoraConsulta());
                dto.setStatusConsulta(consulta.getStatusConsulta());

                return dto;
        }

        public List<ConsultaResponseDTO> listarTodas() {
                return consultaRepository.findAll()
                                .stream()
                                .map(this::converterParaDTO)
                                .toList();
        }

        public List<ConsultaResponseDTO> listarPorMedico(Long idMedico) {
                return consultaRepository.findByMedicoId(idMedico)
                                .stream()
                                .map(this::converterParaDTO)
                                .toList();
        }

        public List<ConsultaResponseDTO> listarPorPaciente(Long idPaciente) {
                return consultaRepository.findByPacienteId(idPaciente)
                                .stream()
                                .map(this::converterParaDTO)
                                .toList();
        }

        public List<ConsultaResponseDTO> listarPorDia(LocalDate data) {
                return consultaRepository.findByDataConsulta(data)
                                .stream()
                                .map(this::converterParaDTO)
                                .toList();
        }

        public List<ConsultaResponseDTO> listarPorSemanaAtual() {
                LocalDate hoje = LocalDate.now();
                LocalDate inicioSemana = hoje.with(DayOfWeek.MONDAY);
                LocalDate fimSemana = hoje.with(DayOfWeek.SUNDAY);

                return consultaRepository.findByDataConsultaBetween(inicioSemana, fimSemana)
                                .stream()
                                .map(this::converterParaDTO)
                                .toList();
        }

        public List<ConsultaSemProntuarioResponseDTO> listarConsultasSemProntuario() {
                return consultaRepository.findByProntuarioIsNull().stream()
                                .map(consulta -> new ConsultaSemProntuarioResponseDTO(
                                                consulta.getIdConsulta(),
                                                consulta.getPaciente().getNome(),
                                                consulta.getMedico().getNome(),
                                                consulta.getDataConsulta(),
                                                consulta.getHoraConsulta()))
                                .toList();
        }

        public ConsultaResponseDTO cancelarConsulta(Long idConsulta) {

                Consulta consulta = consultaRepository.findById(idConsulta)
                                .orElseThrow(() -> new RecursoNaoEncontradoException("Consulta não encontrada"));

                if (consulta.getStatusConsulta() == StatusConsulta.CANCELADO) {
                        throw new RegraNegocioException(
                                        "Consulta já foi cancelada.");
                }

                if (consulta.getStatusConsulta() == StatusConsulta.REALIZADO) {
                        throw new RegraNegocioException(
                                        "Consulta realizada, não pode ser cancelada.");
                }

                consulta.setStatusConsulta(StatusConsulta.CANCELADO);
                logAuditoriaService.registrar(AcaoAuditoriaEnum.UPDATE, "CONSULTA", idConsulta,
                                "A consulta de id: " + consulta.getIdConsulta() + " foi Cancelada");

                Consulta consultaSalva = consultaRepository.save(consulta);
                return converterParaDTO(consultaSalva);
        }
}