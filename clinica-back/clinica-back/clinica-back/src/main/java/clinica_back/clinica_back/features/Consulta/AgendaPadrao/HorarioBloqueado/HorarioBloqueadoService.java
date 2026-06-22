package clinica_back.clinica_back.features.Consulta.AgendaPadrao.HorarioBloqueado;

import clinica_back.clinica_back.features.Consulta.AgendaPadrao.HorarioBloqueado.DTOs.HorarioBloqueadoRequestDTO;
import clinica_back.clinica_back.features.Usuario.Medico.Medico;
import clinica_back.clinica_back.features.Usuario.Medico.MedicoRepository;
import clinica_back.clinica_back.shared.exceptions.RecursoNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HorarioBloqueadoService {

    private final HorarioBloqueadoRepository bloqueioRepository;
    private final MedicoRepository medicoRepository;

    public HorarioBloqueado cadastrar(
            HorarioBloqueadoRequestDTO dto) {

        Medico medico = medicoRepository.findById(dto.getIdMedico())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Médico não encontrado"));


        HorarioBloqueado bloqueio = new HorarioBloqueado();

        bloqueio.setMedico(medico);
        bloqueio.setData(dto.getData());
        bloqueio.setHoraInicio(dto.getHoraInicio());
        bloqueio.setHoraFim(dto.getHoraFim());
        bloqueio.setMotivo(dto.getMotivo());

        return bloqueioRepository.save(bloqueio);
    }
}
