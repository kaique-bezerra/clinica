package clinica_back.clinica_back.features.Chat.Mensagem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import clinica_back.clinica_back.features.Chat.Chat;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {

    List<Mensagem> findByChatOrderByDataHoraAsc(Chat chat);

}
