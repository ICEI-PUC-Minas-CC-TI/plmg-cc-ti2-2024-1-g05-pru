# Prontuário Unificado (PrU)
O objetivo do sistema é reunir num único local as informações de saúde de indivíduos pacientes de determinados profissionais e/ou organizações e criar um vínculo entre eles e as possibilidades oferecidas pelo respectivo consultório ou profissional, de maneira a facilitar atendimentos e mitigar comuns redundâncias vistas nos atuais sistemas médicos.

## Alunos integrantes da equipe
- André Luís Silva de Paula
- João de Jesus e Avila
- Pedro Henrique Soares Rosemberg
- Priscila Andrade de Moraes
- Sirius Victor Zevallos

## Professores responsáveis
- Max do Val Machado
- Walisson Ferreira de Carvalho

## Instruções de utilização
Para usar a aplicação, você precisa ter o Docker e o Docker compose instalado e executar o seguinte comando no terminal:
```
$ docker compose up -d
```
Também é necessário adicionar a configuração da AWS, para o funcionamento do sistema inteligente (reconhecimento facial), para isso, crie uma pasta chamada .aws na home do usuário e adicione um arquivo chamado credentials com o seguinte conteúdo:
```
[default]
aws_access_key_id = <sua chave de acesso>
aws_secret_access_key = <sua chave secreta>
```