-- usuario (medico e paciente)
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

CREATE TABLE public.medico (
  crm VARCHAR(8) NOT NULL,
  usuario_id INT NOT NULL,
  CONSTRAINT medico_pkey PRIMARY KEY (usuario_id),
  CONSTRAINT fk_usuario_id FOREIGN KEY (usuario_id) REFERENCES public.usuario (id)
);

CREATE TABLE public.paciente (
  usuario_id INT NOT NULL,
  CONSTRAINT paciente_pkey PRIMARY KEY (usuario_id),
  CONSTRAINT fk_usuario_id FOREIGN KEY (usuario_id) REFERENCES public.usuario (id)
);

-- consulta
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

-- exame
CREATE TABLE public.exame (
  id SERIAL PRIMARY KEY,
  titulo VARCHAR(255) NOT NULL,
  "data" DATE,
  url_arquivo VARCHAR(255),
  status VARCHAR(30) NOT NULL,
  consulta_id INT NOT NULL,
  CONSTRAINT fk_consulta_id FOREIGN KEY (consulta_id) REFERENCES public.consulta (id)
);

--medicamento
CREATE TABLE public.medicamento (
  id INT PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  dias INT,
  controlado BOOLEAN,
  consulta_id INT NOT NULL,
  CONSTRAINT fk_consulta_id FOREIGN KEY (consulta_id) REFERENCES public.consulta (id)
);

--vinculo
CREATE TABLE public.vinculo (
  id SERIAL PRIMARY KEY,
  status VARCHAR(30),
  paciente_id INT NOT NULL,
  medico_id INT NOT NULL,
  CONSTRAINT fk_paciente_id FOREIGN KEY (paciente_id) REFERENCES public.paciente (usuario_id),
  CONSTRAINT fk_medico_id FOREIGN KEY (medico_id) REFERENCES public.medico (usuario_id)
);
