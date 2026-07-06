package clinica_back.clinica_back.features.Chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import clinica_back.clinica_back.features.Chat.Dto.GeminiResponseDTO;
import clinica_back.clinica_back.features.Chat.Mensagem.Mensagem;
import clinica_back.clinica_back.features.Chat.Mensagem.MensagemRepository;
import clinica_back.clinica_back.features.Chat.Mensagem.PapelMensagemEnum;
import clinica_back.clinica_back.features.Chat.Mensagem.dto.MensagemResponseDTO;
import clinica_back.clinica_back.features.Usuario.Paciente.Paciente;
import clinica_back.clinica_back.features.Usuario.Paciente.PacienteRepository;
import clinica_back.clinica_back.shared.exceptions.RecursoNaoEncontradoException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

        private final PacienteRepository pacienteRepository;
        private final ChatRepository chatRepository;
        private final MensagemRepository mensagemRepository;

        @Value("${gemini.api.key}")
        private String apiKey;

        @Value("${gemini.api.url}")
        private String apiUrl;

        private final WebClient webClient = WebClient.create();

        public String perguntar(String email, String pergunta) {

                String contextoSistema = """
                                Você é o assistente virtual da clínica MedSync.

                                Sua função é responder apenas perguntas relacionadas à clínica.

                                Você pode responder sobre:
                                - agendamento de consultas
                                - cancelamento de consultas
                                - remarcação de consultas
                                - exames
                                - médicos
                                - especialidades
                                - convênios
                                - horários
                                - prontuários
                                - medicações

                                Regras:
                                1. Nunca responda perguntas fora do contexto da clínica.
                                2. Se a pergunta for fora do contexto, responda:
                                'Posso ajudar apenas com assuntos da clínica.'
                                3. Seja objetivo, educado e claro.
                                4. Nunca invente informações.
                                5. Se não souber algo, peça para o paciente procurar a recepção.
                                """;

                String baseConhecimento = """
                                Informações da clínica MedSync:

                                Horário de funcionamento:
                                Segunda a sexta: 08:00 às 18:00

                                sobre o sistema e os desenvolvedores:
                                a medSny e um sistema criado pelos desenvolvedores kaique e jessica com o objetivo de facilitar para medicos
                                e pacientes a comunicação ainda esta sendo desenvolvido então qual quer falha comunique a recepcionista

                                o que e a medSny:
                                A **MedSync** nasceu de uma ideia simples: tornar o atendimento em clínicas mais rápido, organizado e humano.

                                Tudo começou quando um jovem estudante de Sistemas de Informação percebeu as dificuldades que pacientes enfrentavam no dia a dia: filas longas, perda de consultas, falta de comunicação entre recepção, médicos e pacientes, além da dificuldade em acessar prontuários e exames.

                                Pensando nisso, ele decidiu criar uma plataforma capaz de conectar todos esses setores em um único sistema. Assim surgiu a MedSync, um sistema inteligente que sincroniza consultas, prontuários, medicações e atendimentos em tempo real.

                                Com o tempo, a MedSync evoluiu. O que antes era apenas um projeto acadêmico se tornou uma solução moderna, com dashboards personalizados para recepcionistas, médicos, administradores e pacientes, além de um assistente virtual capaz de responder dúvidas e ajudar no agendamento de consultas.

                                O maior objetivo da MedSync sempre foi claro: usar a tecnologia para aproximar pessoas da saúde, trazendo mais eficiência, segurança e praticidade para todos.

                                Hoje, a MedSync representa inovação, organização e cuidado — mostrando que a tecnologia pode transformar vidas.


                                Convênios aceitos:
                                - Unimed
                                - Hapvida
                                - Bradesco Saúde
                                - SulAmérica

                                Endereço:
                                - Paraiba
                                - Campina Grande
                                - prata
                                - rua aprigio pereira nepumuceno

                                Exames disponíveis:
                                - Hemograma
                                - Exame de sangue
                                - Ultrassonografia
                                - Raio-X
                                - Eletrocardiograma

                                Regras de remarcação:
                                - Pode remarcar até 24 horas antes da consulta

                                Regras de cancelamento:
                                - Cancelamento permitido até 24 horas antes

                                Orientações:
                                - Trazer documento com foto
                                - Cartão do convênio
                                - Exames anteriores (se houver)

                                Contato da recepção:
                                (83) 3333-0000
                                """;

                String promptFinal = """
                                %s

                                %s

                                Pergunta do paciente:
                                %s
                                """.formatted(
                                contextoSistema,
                                baseConhecimento,
                                pergunta);

                String body = """
                                {
                                  "contents": [{
                                    "parts": [{
                                      "text": "%s"
                                    }]
                                  }]
                                }
                                """.formatted(promptFinal);

                Paciente paciente = pacienteRepository.findByEmail(email)
                                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado"));

                Chat chat = chatRepository.findByPaciente(paciente).orElseGet(() -> {
                        Chat novoChat = new Chat();
                        novoChat.setPaciente(paciente);
                        return chatRepository.save(novoChat);

                });

                Mensagem msgUsuario = new Mensagem();
                msgUsuario.setChat(chat);
                msgUsuario.setConteudo(pergunta);
                msgUsuario.setPapel(PapelMensagemEnum.USER);

                mensagemRepository.save(msgUsuario);

                GeminiResponseDTO response = webClient.post()
                                .uri(apiUrl + "?key=" + apiKey)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(body)
                                .retrieve()
                                .bodyToMono(GeminiResponseDTO.class)
                                .block();

                if (response == null
                                || response.candidates() == null
                                || response.candidates().isEmpty()
                                || response.candidates().get(0).content() == null
                                || response.candidates().get(0).content().parts() == null
                                || response.candidates().get(0).content().parts().isEmpty()) {
                        return "Não consegui gerar uma resposta agora.";
                }

                String respostaIA = response.candidates()
                                .get(0)
                                .content()
                                .parts()
                                .get(0)
                                .text();

                Mensagem msgIA = new Mensagem();
                msgIA.setChat(chat);
                msgIA.setConteudo(respostaIA);
                msgIA.setPapel(PapelMensagemEnum.ASSISTANT);

                mensagemRepository.save(msgIA);
                return respostaIA;
        }

        public List<MensagemResponseDTO> buscarHistorico(String email) {

                Paciente paciente = pacienteRepository.findByEmail(email)
                                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado"));

                Chat chat = chatRepository.findByPaciente(paciente)
                                .orElseThrow(() -> new RecursoNaoEncontradoException("Chat não encontrado"));

                return mensagemRepository.findByChatOrderByDataHoraAsc(chat)
                                .stream()
                                .map(mensagem -> new MensagemResponseDTO(
                                                mensagem.getIdMensagem(),
                                                mensagem.getConteudo(),
                                                mensagem.getPapel(),
                                                mensagem.getDataHora()))
                                .toList();
        }
}