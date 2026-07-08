package clinica_back.clinica_back.features.Consulta.Disponibilidade;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

import clinica_back.clinica_back.features.Consulta.Consulta;
import clinica_back.clinica_back.features.Consulta.ConsultaRepository;
import clinica_back.clinica_back.features.Consulta.AgendaPadrao.AgendaPadrao;
import clinica_back.clinica_back.features.Consulta.AgendaPadrao.AgendaPadraoRepository;
import clinica_back.clinica_back.features.Consulta.AgendaPadrao.HorarioBloqueado.HorarioBloqueado;
import clinica_back.clinica_back.features.Consulta.AgendaPadrao.HorarioBloqueado.HorarioBloqueadoRepository;
import clinica_back.clinica_back.features.Consulta.Disponibilidade.DTOs.HorarioDisponivelDTO;
import clinica_back.clinica_back.features.Usuario.Medico.Medico;
import clinica_back.clinica_back.features.Usuario.Medico.MedicoRepository;
import clinica_back.clinica_back.shared.exceptions.RecursoNaoEncontradoException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DisponibilidadeService {

    private final MedicoRepository medicoRepository;
    private final AgendaPadraoRepository agendaRepository;
    private final ConsultaRepository consultaRepository;
    private final HorarioBloqueadoRepository bloqueioRepository;

    public List<HorarioDisponivelDTO> listarHorariosDisponiveis(
            Long idMedico,
            LocalDate data) {

        Medico medico = medicoRepository.findById(idMedico)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Médico não encontrado"));


        DayOfWeek diaSemana = data.getDayOfWeek();

        List<AgendaPadrao> agendas = agendaRepository
                .findByMedicoAndDiaSemana(medico, diaSemana);

        if (agendas.isEmpty()) {
            throw new RecursoNaoEncontradoException("Médico não atende nesse dia");
        }

        Set<LocalTime> slots = new TreeSet<>();

        for (AgendaPadrao agenda : agendas) {
            slots.addAll(gerarSlots(
                    agenda.getHoraInicio(),
                    agenda.getHoraFim(),
                    agenda.getIntervaloMinutos()
            ));
        }

        List<Consulta> consultas = consultaRepository
                .findByMedicoAndDataConsulta(medico, data);

        List<HorarioBloqueado> bloqueios = bloqueioRepository
                .findByMedicoAndData(medico, data);

        List<HorarioDisponivelDTO> livres = new ArrayList<>();

        for (LocalTime slot : slots) {

            boolean ocupado = consultas.stream()
                    .anyMatch(c -> c.getHoraConsulta().equals(slot));

            boolean bloqueado = bloqueios.stream()
                    .anyMatch(b ->
                            !slot.isBefore(b.getHoraInicio()) &&
                                    slot.isBefore(b.getHoraFim())
                    );

            if (!ocupado && !bloqueado) {
                livres.add(new HorarioDisponivelDTO(slot));
            }
        }
        return livres;
    }
    private List<LocalTime> gerarSlots(
            LocalTime inicio,
            LocalTime fim,
            Integer intervalo) {

        List<LocalTime> slots = new ArrayList<>();
        LocalTime atual = inicio;

        while (atual.isBefore(fim)) {
            slots.add(atual);
            atual = atual.plusMinutes(intervalo);
        }
        return slots;
    }
}