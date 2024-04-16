Criar tabela usuários

```sql
CREATE TABLE usuarios (
  id SERIAL PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  cpf VARCHAR(11) NOT NULL,
  email VARCHAR(255) NOT NULL,
  senha VARCHAR(255) NOT NULL,
  telefone VARCHAR(11),
  sexo CHAR(1),
  nascimento DATE,
  urlFoto VARCHAR(255),
  cep VARCHAR(8)
);
```

Criar tabela médico
```sql
CREATE TABLE medico (
  crm VARCHAR(8) NOT NULL,
  usuario_id INTEGER PRIMARY KEY,
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);
```

Criar tabela paciente
```sql
CREATE TABLE medico (
  usuario_id INTEGER PRIMARY KEY,
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);
```

Inserir um registro na tabela usuario
```sql
INSERT INTO usuario (nome, cpf, email, senha, telefone, sexo, nascimento, urlfoto, cep) 
VALUES ('Dr. Mock', '12345678901', 'dr.mock@example.com', 'password', '1234567890', 'M', '1980-01-01', 'http://example.com/photo.jpg', '12345678');
```

Inserir um registro na tabela medico
```sql
INSERT INTO medico (usuario_id, crm) 
VALUES (3, '1234567');
```