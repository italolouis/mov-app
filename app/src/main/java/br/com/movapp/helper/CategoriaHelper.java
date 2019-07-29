package br.com.movapp.helper;

import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.movapp.controller.CategoriaController;
import br.com.movapp.model.Categoria;

public class CategoriaHelper {
    public ArrayList<CategoriaHelper> children;
    public ArrayList<String> selection;
    private static CategoriaController categoriaController;

    public String name;

    public CategoriaHelper() {
        children = new ArrayList<CategoriaHelper>();
        selection = new ArrayList<String>();
    }

    public CategoriaHelper(Context context){
        categoriaController = new CategoriaController(context);
    }

    public CategoriaHelper(String name) {
        this();
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    private void generateChildren(Long parentId) {
        List<Categoria> subCategorias = categoriaController.buscarSubCategorias(parentId);
        for(Categoria subCategoria: subCategorias){
            CategoriaHelper cat = new CategoriaHelper(subCategoria.getNome());
            this.children.add(cat);
        }
    }

    public static ArrayList<CategoriaHelper> getCategories() {
        ArrayList<CategoriaHelper> categories = new ArrayList<CategoriaHelper>();
        List<Categoria> categorias = categoriaController.buscarCategorias();
        for(Categoria categoria: categorias){
            CategoriaHelper cat = new CategoriaHelper(categoria.getNome());
            cat.generateChildren(categoria.getCod());
            categories.add(cat);
        }
        return categories;
    }

    public static CategoriaHelper get(String name) {
        ArrayList<CategoriaHelper> collection = CategoriaHelper.getCategories();
        for (Iterator<CategoriaHelper> iterator = collection.iterator(); iterator.hasNext();) {
            CategoriaHelper cat = (CategoriaHelper) iterator.next();
            if(cat.name.equals(name)) {
                return cat;
            }

        }
        return null;
    }
}
