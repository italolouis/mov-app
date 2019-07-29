package br.com.movapp.activity.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.movapp.R;
import br.com.movapp.controller.UsuarioController;
import br.com.movapp.model.Usuario;
import br.com.movapp.utils.DateUtils;
import br.com.movapp.utils.ImageUtils;

public class HomeFragment extends Fragment {
    private TextView textHomeBemVindo;
    private ImageView imageViewProfile;
    private TextView textViewTreino;
    private UsuarioController usuarioController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        usuarioController = new UsuarioController(container.getContext());
        Usuario usuario = usuarioController.buscaUsuario();

        textHomeBemVindo = (TextView) view.findViewById(R.id.textHomeBemVindo);
        textHomeBemVindo.setText(getMessageWelcome(usuario).concat(usuario.getNome()));

        imageViewProfile = (ImageView) view.findViewById(R.id.imageViewProfile);
        if(usuario.getFoto() != null){
            Bitmap fotoProfile = ImageUtils.resizeImage(usuario.getFoto(), imageViewProfile.getLayoutParams().width+100, imageViewProfile.getLayoutParams().height+180);
            imageViewProfile.setImageBitmap(ImageUtils.getCroppedBitmap(fotoProfile));
        }

        textViewTreino = (TextView) view.findViewById(R.id.textViewTreino);
        textViewTreino.setText(DateUtils.getDayOfWeek());

        return view;
    }

    public String getMessageWelcome(Usuario usuario){
        if(usuario.getGenero() != null){
            if(usuario.getGenero().equals("F")){
                return "Bem vinda, ";
            }else if(usuario.getGenero().equals("M")){
                return "Bem vindo, ";
            }
        }

        return "Bem vindx, ";
    }
}
