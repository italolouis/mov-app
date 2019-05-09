package br.com.movapp.activity.fragments;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import br.com.movapp.dao.UsuarioDAO;
import br.com.movapp.model.Usuario;
import br.com.movapp.utils.DateUtils;
import br.com.movapp.utils.ImageUtils;


public class HomeFragment extends Fragment {
    private TextView textHomeBemVindo;
    private ImageView imageViewProfile;
    private TextView textViewTreino;
    private UsuarioDAO usuarioDAO;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        usuarioDAO = new UsuarioDAO(container.getContext());
        Usuario usuario = usuarioDAO.buscaUsuario();

        textHomeBemVindo = (TextView) view.findViewById(R.id.textHomeBemVindo);
        textHomeBemVindo.setText("Bem vindo, " + usuario.getNome());

        imageViewProfile = (ImageView) view.findViewById(R.id.imageViewProfile);
        Bitmap fotoProfile = ImageUtils.getBitmapFromBytes(usuario.getFoto());
        fotoProfile = Bitmap.createScaledBitmap(fotoProfile, imageViewProfile.getLayoutParams().width+100, imageViewProfile.getLayoutParams().height+180, true);
        imageViewProfile.setImageBitmap(ImageUtils.getCroppedBitmap(fotoProfile));

        textViewTreino = (TextView) view.findViewById(R.id.textViewTreino);
        textViewTreino.setText(DateUtils.getDayOfWeek());

        return view;
    }
}
