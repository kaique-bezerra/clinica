package clinica_back.clinica_back.features.Consulta.Prontuario;

import clinica_back.clinica_back.features.Consulta.Consulta;
import clinica_back.clinica_back.features.Consulta.ConsultaRepository;
import clinica_back.clinica_back.features.Consulta.Prontuario.DTOs.ProntuarioRequestDTO;
import clinica_back.clinica_back.features.Consulta.Prontuario.DTOs.ProntuarioResponseDTO;
import clinica_back.clinica_back.shared.exceptions.RecursoNaoEncontradoException;
import clinica_back.clinica_back.shared.exceptions.RegraNegocioException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ProntuarioService {

    private final ProntuarioRepository prontuarioRepository;
    private final ConsultaRepository consultaRepository;

    public ProntuarioResponseDTO cadastrar(
            ProntuarioRequestDTO dto) {

        Consulta consulta = consultaRepository.findById(
                dto.getIdConsulta()
        ).orElseThrow(() ->
                new RecursoNaoEncontradoException(
                        "Consulta não encontrada"));

        if (consulta.getStatusConsulta().name()
                .equals("CANCELADO")) {
            throw new RegraNegocioException(
                    "Consulta cancelada não pode ter prontuário");
        }

        if (consulta.getProntuario() != null) {
            throw new RegraNegocioException(
                    "Essa consulta já possui prontuário");
        }

        Prontuario prontuario = new Prontuario();

        prontuario.setConsulta(consulta);
        prontuario.setQueixas(dto.getQueixas());
        prontuario.setDiagnostico(dto.getDiagnostico());
        prontuario.setObservacoes(dto.getObservacoes());
        prontuario.setPrescricao(dto.getPrescricao());

        Prontuario salvo = prontuarioRepository.save(prontuario);

        consulta.setStatusConsulta(clinica_back.clinica_back.features.Consulta.StatusConsulta.REALIZADO);
        consultaRepository.save(consulta);


        return ProntuarioResponseDTO.builder()
                .idProntuario(salvo.getIdProntuario())
                .idConsulta(salvo.getConsulta().getIdConsulta())
                .queixas(salvo.getQueixas())
                .diagnostico(salvo.getDiagnostico())
                .observacoes(salvo.getObservacoes())
                .prescricao(salvo.getPrescricao())
                .build();
    }
}