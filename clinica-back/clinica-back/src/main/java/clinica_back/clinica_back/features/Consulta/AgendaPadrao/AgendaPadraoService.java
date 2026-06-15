package clinica_back.clinica_back.features.Consulta.AgendaPadrao;

import clinica_back.clinica_back.features.Consulta.AgendaPadrao.DTOs.AgendaPadraoRequestDTO;
import clinica_back.clinica_back.shared.exceptions.RecursoNaoEncontradoException;
import clinica_back.clinica_back.shared.exceptions.RegraNegocioException;
import org.springframework.stereotype.Service;
import clinica_back.clinica_back.features.Usuario.Medico.Medico;
import clinica_back.clinica_back.features.Usuario.Medico.MedicoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgendaPadraoService {

    private final AgendaPadraoRepository agendaRepository;
    private final MedicoRepository medicoRepository;

    public AgendaPadrao cadastrar(AgendaPadraoRequestDTO dto) {

        Medico medico = medicoRepository.findById(dto.getIdMedico())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Médico não encontrado"));

        AgendaPadrao agenda = new AgendaPadrao();
        if(dto.getHoraInicio().isAfter(dto.getHoraFim())) {
            throw new RegraNegocioException(
                    "Hora inicial não pode ser maior que a final."
            );
        }

        if(dto.getIntervaloMinutos() <= 0 ) {
            throw new RegraNegocioException(
                    "Intervalo inválido."
            );
        }
        agenda.setMedico(medico);
        agenda.setDiaSemana(dto.getDiaSemana());
        agenda.setHoraInicio(dto.getHoraInicio());
        agenda.setHoraFim(dto.getHoraFim());
        agenda.setIntervaloMinutos(dto.getIntervaloMinutos());

        return agendaRepository.save(agenda);
    }
}