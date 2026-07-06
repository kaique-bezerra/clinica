package clinica_back.clinica_back.features.Chat;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import clinica_back.clinica_back.features.Usuario.Paciente.Paciente;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findByPaciente(Paciente paciente);

}
