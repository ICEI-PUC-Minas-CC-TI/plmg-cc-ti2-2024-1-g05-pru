# Código

### Estrutura de diretórios
```
codigo
  |- src
  |  |- main
  |  |  |- java
  |  |  |  |- app
  |  |  |  |- dao
  |  |  |  |- model
  |  |  |  |- service 
  |  |  |  |- util
  |  |  |- resources
  |  |  |  |- site (front-end)
  |  |  |  |- sql
  |  |  |  |  |- create.sql
  |  |  |  |  |- README.md
  |- README.md

```

## Rotas da API
### /login
  - (POST) **/login** - Autenticação do usuário

### /medico
  - (GET) **/medico/:id** - Busca de médico por id
  - (POST) **/medico/** - Cadastro de médico
  - (PUT) **/medico/:id** - Atualização de médico 
  - (DELETE) **/medico/:id** - Exclusão de médico
  
  ####
  - (GET) **/medico/:id/pacientes** - Busca de pacientes vinculados ao médico
  - (GET) **/medico/:id/especialidades** - Busca de especialidades do médico

### /paciente
  - (GET) **/paciente/:id** - Busca de paciente por id
  - (POST) **/paciente/** - Cadastro de paciente
  - (PUT) **/paciente/:id** - Atualização de paciente
  - (DELETE) **/paciente/:id** - Exclusão de paciente

  ####
  - (GET) **/paciente/:id/medicos** - Busca de vinculos do paciente
  - (GET) **/paciente/:id/consultas** - Busca de consultas do paciente
  - (GET) **/paciente/:id/consultas/:qtde** - Busca as últimas consultas do paciente
  - (GET) **/paciente/:id/exames** - Busca de exames do paciente
  - (GET) **/paciente/:id/exames/:qtde** - Busca os últimos exames do paciente

### /consulta
  - (GET) **/consulta/:id** - Busca de consulta por id
  - (POST) **/consulta/** - Cadastro de consulta
  - (PUT) **/consulta/:id** - Atualização de consulta
  - (DELETE) **/consulta/:id** - Exclusão de consulta
  
  ####
  - (GET) **/consulta/:id/exames** - Busca de todos exames da consulta
  - (GET) **/consulta/:id/medicamento** - Busca de todos os medicamentos da consulta

### /exame
  - (GET) **/exame/:id** - Busca de exame por id
  - (POST) **/exame/** - Cadastro de exame
  - (PUT) **/exame/:id** - Atualização de exame
  - (DELETE) **/exame/:id** - Exclusão de exame

### /medicamento
  - (POST) **/medicamento/** - Cadastro de medicamento
  - (PUT) **/medicamento/:id** - Atualização de medicamento
  - (DELETE) **/medicamento/:id** - Exclusão de medicamento

### /vinculo
  - (POST) **/vinculo/** - Cadastro de vinculo
  - (PUT) **/vinculo/:id** - Atualização de vinculo
  - (DELETE) **/vinculo/:id** - Exclusão de vinculo

### /especialidade
  - (POST) **/medico/:id/especialidades** - Cadastro de especialidade
  - (PUT) **/especialidade/:id** - Atualização de especialidade
  - (DELETE) **/especialidade/:id** - Exclusão de especialidade
