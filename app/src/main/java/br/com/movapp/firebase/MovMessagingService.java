package br.com.movapp.firebase;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.util.Map;

import br.com.movapp.dao.PessoaDAO;
import br.com.movapp.dto.PessoaSync;

public class MovMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
      super.onMessageReceived(remoteMessage);

        Map<String, String> mensagem = remoteMessage.getData();
        Log.i("mensagem recebida", String.valueOf(mensagem));
        
        convertParaPessoa(mensagem);
    }

    private void convertParaPessoa(Map<String, String> mensagem) {
        String chaveDeAcesso = "pessoa";
        if(mensagem.containsKey(chaveDeAcesso)){
            String json = mensagem.get(chaveDeAcesso);
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                PessoaSync pessoaSync = objectMapper.readValue(json, PessoaSync.class);
                PessoaDAO pessoaDAO = new PessoaDAO(this);
                pessoaDAO.sincroniza(pessoaSync.getPessoas());

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
