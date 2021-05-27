# Sistema de Produtividade Acadêmica

## Como executar:

Para executar o sistema, basta compilar todas as classes java juntas e executar a classe Server e, em outro terminal, executar a classe Client. Isso pode ser feito pelo terminal executando os seguintes comandos na pasta raiz do projeto:

### No Windows:

* dir /s /B *.java > sources.txt
* javac @sources.txt -d .\build
* java -cp .\build Server
* java -cp .\build Client

### No Linux:

* find -name "*.java" > sources.txt
* javac @sources.txt -d .\build
* java -cp .\build Server
* java -cp .\build Client

## Para fazer login como administrador:

* Usuário: admin
* Senha: 1234

Também foram adicionados dois colaboradores nos quais é possível fazer login: 

* Ana Santos (email: ana@email.com, senha: 123)
* José Pereira (email: jose@email.com , senha: 123)


## Funcionalidades do sistema:

### Login como administrador:

O administrador é responsável pela manutenção de todas as informações do sistema. Assim, é ele o responsável por adicionar novos colaboradores, projetos e produções acadêmicas ao sistema. Também é possível editar as informações de colaboradores e projetos já adicionados.

### Login como colaborador:

Os colaboradores podem fazer login no sistema através de seu email e senha. Os colaboradores não tem permissão de modificar as informações do sistema, sendo possível apenas consultar as informações dos projetos, dos colaboradores e das produções acadêmicas.

### Alocação de participantes:

A alocação e desalocação de participantes é permitida apenas quando o projeto está “em elaboração”. Um aluno de graduação não pode estar participando de mais de dois projetos não concluídos. 

### Alteração do status

O administrador pode mudar o status de um projeto de "em elaboração" para "em andamento" apenas quando se constarem todas as informações básicas a respeito do projeto cadastradas (título, data de início, data de término, agência financiadora, valor financiado, objetivo, descrição e participantes). Deve haver pelo menos um professor alocado para um projeto entrar "em andamento".
O administrador pode mudar o status de um projeto de "em andamento" para "concluído" apenas se existir alguma publicação associada ao projeto.

### Produção acadêmica

O sistema permite a inclusão de informações referentes à produção acadêmica (orientações e publicações). Uma orientação é feita por um professor que orienta um aluno do laboratório. Uma orientação possui título e ano de publicação.
Todos os colaboradores podem ter publicações, e uma publicação pode ter vários autores. Uma publicação possui título, nome da conferência onde foi publicada, ano de publicação e projeto de pesquisa associado (se houver). Uma publicação só poderá ser associada a um projeto quando o status do projeto
estiver “em andamento”.

### Consulta

* Consulta por colaborador: Através do nome ou email informado, o sistema mostrará uma lista de colaboradores correspondente. Selecionando o colaborador desejado, o sistema mostrará suas informações(nome, email, um histórico contendo a lista de projetos nos quais este colaborador participa ou participou, ordenados de forma decrescente pela data de término, e a lista de sua produção acadêmica).
* Consulta por projeto: Através do título informado, o sistema mostrará uma lista de projetos correspondente. Selecionando o projeto desejado, o sistema mostrará seus dados (incluindo colaboradores alocados e uma lista contendo toda a produção acadêmica do projeto, ordenada de forma decrescente de data (ano)).
* Consulta por produção acadêmica: Através do título da publicação ou orientação, o sistema informará uma lista de produções acadêmicas correspondente. Selecionando a produção acadêmica desejada, o sistema mostrará seus dados.

### Relatório de produtividade acadêmica

O sistema fornece um relatório de produção acadêmica do laboratório, contendo:
* Número de colaboradores;
* Número de projetos em elaboração;
* Número de projetos em andamento;
* Número de projetos concluídos;
* Número total de projetos;
* Número de produção acadêmica por tipo de produção.
