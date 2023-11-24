# LeiaPraMim
> Aplicativo de acessibilidade para leitura de textos

O **LeiaPraMim** foi desenvolvido com um propósito fundamental: proporcionar acesso à informação para indivíduos que enfrentam desafios na leitura. Projetado especialmente para atender pessoas que não têm habilidades de leitura, o **LeiaPraMim** utiliza tecnologia avançada de Reconhecimento Óptico de Caracteres (OCR) e Text to Speech, para converter texto de imagens em palavras faladas.

## Melhorias adicionadas com a API
- Todos os dados estão sendo guardados no Banco de Dados, e podem ser recuperados mesmo depois de uma desinstalação.
- Também podem ser recuperados via *client* através dos *endpoints*.
- No caso do usuário não dispor de internet, os dados serão mantidos também local.
- Login é feito sem necessidade de intervenção do usuário, através do id do dispositivo.

## Tecnologias utilizadas

### No APP
- *Plataforma*: Android (Versão mínima: Android 8.1 "Oreo")
- *IDE*: Android Studio
- *Linguagem de Programação*: Kotlin com Jetpack Compose
- *Design System*: Material Design
- *Navegação*: Navigation Compose
- *Gerenciamento de Estado*: Lifecycle e LiveData
- *Banco de Dados*: Room
- *Requisições REST*: Retrofit2 e Gson

### Na API
- Oracle Database
- Java Spring Boot
- Java Database Connectivity
- Java Persistence API
- Data Rest

## APIs Externas Integradas

- [**Voice RSS**](https://www.voicerss.org/api/) - Text-to-Speech API
- [**OCR Space**](https://ocr.space/ocrapi) - Optical Character Recognition
