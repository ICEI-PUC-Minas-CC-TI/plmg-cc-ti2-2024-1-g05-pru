package service;

import model.Exame;
import dao.ExameDAO;
import util.GsonUtil;

import java.util.List;
import spark.Request;
import spark.Response;

public class ExameService {
    public class ExameService {
        private ExameDAO exameDAO;
    
        public ExameService() {
            exameDAO = new ExameDAO();
        }
    
        public Object read(Request request, Response response) {
            int id = Integer.parseInt(request.params(":id"));
            Exame exame = ExameDAO.get(id);
    
            if (exame != null) {
                response.header("Content-Type", "application/json");
                response.header("Content-Encoding", "UTF-8");
    
                return GsonUtil.GSON.toJson(exame);
            } else {
                response.status(404); // 404 Not found
                return "Exame ID #" + id + " não encontrada.";
            }
        }
    
        public Object create(Request request, Response response) {
            Exame Exame = GsonUtil.GSON.fromJson(request.body(), Exame.class);
    
            if (ExameDAO.insert(exame)) {
                response.status(201); // 201 Created
                return "Exame (" + exame.getTitulo() + ") inserida!";
            } else {
                response.status(404); // 404 Not found
                return "Exame (" + exame.getTitulo() + ") não inserida!";
            }
        }
    
        public Object update(Request request, Response response) {
            int id = Integer.parseInt(request.params(":id"));
            Exame exame = GsonUtil.GSON.fromJson(request.body(), Exame.class);
    
            if (Exame.getId() != id) {
                response.status(400); // 400 Bad request
                return "ID da Exame diferente do ID na URL!";
            }
    
            if (ExameDAO.update(exame)) {
                response.status(200); // 200 OK
                return "Exame (ID #" + exame.getId() + ") atualizada!";
            } else {
                response.status(404); // 404 Not found
                return "Exame (ID #" + exame.getId() + ") não encontrada!";
            }
        }
    
        public Object delete(Request request, Response response) {
            int id = Integer.parseInt(request.params(":id"));
    
            if (ExameDAO.delete(id)) {
                response.status(200); // 200 OK
                return "Exame (" + id + ") excluída!";
            } else {
                response.status(404); // 404 Not found
                return "Exame (" + id + ") não encontrada!";
            }
        }
    }
}
