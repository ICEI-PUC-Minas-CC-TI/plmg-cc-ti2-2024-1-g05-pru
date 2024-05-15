# Scripts SQL

## Usuários (médico e paciente)
- **Criar tabela usuário**
  ```sql
  CREATE TABLE public.usuario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    email VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    telefone VARCHAR(11) NOT NULL,
    sexo CHAR(1),
    nascimento DATE,
    url_foto VARCHAR(255),
    cep VARCHAR(8) NOT NULL,
    CONSTRAINT unique_cpf UNIQUE (cpf),
    CONSTRAINT unique_email UNIQUE (email)
  );
  ```

- **Criar tabela médico**
  ```sql
  CREATE TABLE public.medico (
    crm VARCHAR(8) NOT NULL,
    usuario_id INT NOT NULL,
    CONSTRAINT medico_pkey PRIMARY KEY (usuario_id),
    CONSTRAINT fk_usuario_id FOREIGN KEY (usuario_id) REFERENCES public.usuario (id)
  );
  ```

- **Criar tabela paciente**
  ```sql
  CREATE TABLE public.paciente (
    usuario_id INT NOT NULL,
    CONSTRAINT paciente_pkey PRIMARY KEY (usuario_id),
    CONSTRAINT fk_usuario_id FOREIGN KEY (usuario_id) REFERENCES public.usuario (id)
  );
  ```
### Inserir registros para teste
Para criar médicos ou pacientes, é necessário inserir registros na tabela `usuario` primeiro e depois na tabela `medico` ou `paciente`, com suas respectivas chaves estrangeiras.

**Observação:** Não recomendado, pois a senha é armazenada sem criptografia e a aplicação considera que a senha está criptografada.

- **Inserir médico**
  ```sql
  INSERT INTO public.usuario (nome, cpf, email, senha, telefone, sexo, nascimento, url_foto, cep)
  VALUES 
    ('Médico 1', '12345678901', 'medico1@example.com', 'senha123', '123456789', 'M', '1980-01-01', NULL, '12345678'),
    ('Médico 2', '23456789012', 'medico2@example.com', 'senha456', '234567890', 'M', '1985-05-15', NULL, '23456789'),
    ('Médico 3', '34567890123', 'medico3@example.com', 'senha789', '345678901', 'M', '1976-10-20', NULL, '34567890'),
    ('Médico 4', '45678901234', 'medico4@example.com', 'senhaabc', '456789012', 'M', '1990-03-08', NULL, '45678901'),
    ('Médico 5', '56789012345', 'medico5@example.com', 'senhadef', '567890123', 'M', '1988-12-25', NULL, '56789012');

  INSERT INTO public.medico (crm, usuario_id)
  VALUES 
    ('CRM12345', 1),
    ('CRM23456', 2),
    ('CRM34567', 3),
    ('CRM45678', 4),
    ('CRM56789', 5);
  ```	

- **Inserir paciente**
  ```sql
  INSERT INTO public.usuario (nome, cpf, email, senha, telefone, sexo, nascimento, url_foto, cep)
  VALUES 
    ('Paciente 1', '67890123456', 'paciente1@example.com', 'senha123', '678901234', 'F', '1995-04-10', NULL, '67890123'),
    ('Paciente 2', '78901234567', 'paciente2@example.com', 'senha456', '789012345', 'M', '1987-07-20', NULL, '78901234'),
    ('Paciente 3', '89012345678', 'paciente3@example.com', 'senha789', '890123456', 'F', '1979-11-05', NULL, '89012345'),
    ('Paciente 4', '90123456789', 'paciente4@example.com', 'senhaabc', '901234567', 'M', '1992-08-15', NULL, '90123456'),
    ('Paciente 5', '01234567890', 'paciente5@example.com', 'senhadef', '012345678', 'F', '1983-12-30', NULL, '01234567');

  INSERT INTO public.paciente (usuario_id)
  VALUES 
    (6),
    (7),
    (8),
    (9),
    (10);
  ```

## Consultas
- Criar tabela consulta
  ```sql
  CREATE TABLE public.consulta (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    diagnostico VARCHAR(1023) NOT NULL,
    data_hora TIMESTAMP NOT NULL,
    url_anexo VARCHAR(255),
    paciente_id INT NOT NULL,
    medico_id INT NOT NULL,
    CONSTRAINT fk_paciente_id FOREIGN KEY (paciente_id) REFERENCES public.paciente (usuario_id),
    CONSTRAINT fk_medico_id FOREIGN KEY (medico_id) REFERENCES public.medico (usuario_id)
  );
  ```

- **Inserir consulta**
  ```sql
  INSERT INTO public.consulta (titulo, diagnostico, data_hora, url_anexo, paciente_id, medico_id)
  VALUES 
    ('Consulta 1', 'Diagnóstico 1', '2021-10-01 08:00:00', NULL, 6, 1),
    ('Consulta 2', 'Diagnóstico 2', '2021-10-02 09:00:00', NULL, 7, 2),
    ('Consulta 3', 'Diagnóstico 3', '2021-10-03 10:00:00', NULL, 8, 3),
    ('Consulta 4', 'Diagnóstico 4', '2021-10-04 11:00:00', NULL, 9, 4),
    ('Consulta 5', 'Diagnóstico 5', '2021-10-05 12:00:00', NULL, 10, 5);
  ```

## Exames
- **Criar tabela exame**
  ```sql
  CREATE TABLE public.exame (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    "data" DATE,
    url_arquivo VARCHAR(255),
    status VARCHAR(30) NOT NULL,
    consulta_id INT NOT NULL,
    CONSTRAINT fk_consulta_id FOREIGN KEY (consulta_id) REFERENCES public.consulta (id)
  );
  ```

- **Inserir exame**
  ```sql
  INSERT INTO public.exame (titulo, "data", url_arquivo, consulta_id)
  VALUES
    ('Exame 1', '2021-10-01', NULL, 1),
    ('Exame 2', '2021-10-02', NULL, 2),
    ('Exame 3', '2021-10-03', NULL, 3),
    ('Exame 4', '2021-10-04', NULL, 4),
    ('Exame 5', '2021-10-05', NULL, 5);
  ```

## Medicamentos
- **Criar tabela medicamento**
  ```sql
  CREATE TABLE public.medicamento (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    dias INT,
    consulta_id INT NOT NULL,
    CONSTRAINT fk_consulta_id FOREIGN KEY (consulta_id) REFERENCES public.consulta (id)
  );
  ```
- **Inserir medicamento**
  ```sql
  INSERT INTO public.medicamento (nome, dias, consulta_id)
  VALUES
    ('Medicamento 1', 7, 1),
    ('Medicamento 2', 14, 2),
    ('Medicamento 3', 21, 3),
    ('Medicamento 4', 28, 4),
    ('Medicamento 5', 30, 5);
  ```
  
## Vínculo
- **Criar tabela vinculo**
  ```sql
  CREATE TABLE vinculo (
    id SERIAL PRIMARY KEY,
    status VARCHAR(30),
    paciente_id INT NOT NULL,
    medico_id INT NOT NULL,
    CONSTRAINT fk_paciente_id FOREIGN KEY (paciente_id) REFERENCES public.paciente (usuario_id),
    CONSTRAINT fk_medico_id FOREIGN KEY (medico_id) REFERENCES public.medico (usuario_id)
  );
  ```
- **Inserir vínculo**
  ```sql
  INSERT INTO vinculo (status, paciente_id, medico_id)
  VALUES
    ('Ativo', 6, 1),
    ('Ativo', 7, 2),
    ('Pendente', 8, 3),
    ('Ativo', 9, 4),
    ('Revogado', 10, 5);
  ```
