package projetandroid.entmobile;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Notes extends ActionBarActivity{

    private String[] drawerListViewItems;
    private ListView drawerListView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private CharSequence titleBar;
    private CharSequence titleMenu;
    private Intent intent;
    private Spinner notes_select_ue;
    private Spinner select_student;
    private TableLayout table_notes;
    private Animation anim_clic_on_item;
    private String ue_selected;
    private AlertDialog.Builder alertEditNoteBuilder;
    private EditText editNote;
    private String note_modified;
    private int index_num_etudiant = 0;
    private int index_nom_etudiant = 1;
    private int index_prenom_etudiant = 2;
//    private int index_filiere = 3;
    private int index_note = 3;
    private int index_nom_exam = 4;
    private ArrayAdapter<String> spinnerAdapter;
    private TableRow row_results;
    private TextView col_num_etudiant;
    private TextView col_nom_etudiant;
    private TextView col_prenom_etudiant;
    private TextView col_filiere;
    private TextView col_note;
    private TextView col_nom_exam;
    private AlertDialog alertEditNote;
    private AlertDialog.Builder alertAddNoteBuilder;
    private AlertDialog alertAddNote;
    private String select_eleve_confirm;
    private EditText nameExam;
    private int set_add_button = 0;
    private int bool_student_not_empty = 0;
    private int bool_note_not_empty = 0;
    private int bool_exam_not_empty = 0;
    private ArrayList<String> col_1;
    private ArrayList<String> col_2;
    private ArrayList<String> col_3;
    private ArrayList<String> col_4;
    private ArrayList<String> col_5;
    private ArrayList<String> col_6;
    private AlertDialog alertDeleteNote;
    private AlertDialog.Builder alertDeleteNoteBuilder;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        //Définition des titres
        titleBar = getResources().getString(R.string.label_notes);
        titleMenu = getResources().getString(R.string.label_menu);

        //Définition des éléments de la page
        notes_select_ue = (Spinner)findViewById(R.id.notes_select_ue);
        table_notes = (TableLayout)findViewById(R.id.table_notes);
        init_col_table_notes();
        setContentListeUE();

        //Définition des animations
        anim_clic_on_item = AnimationUtils.loadAnimation(this, R.anim.fadein);

        //Définition des listners
        selection_setListner();

        //Définition de la boîte de dialogue de confirmation de modification de la note
        alertEditNoteBuilder = new AlertDialog.Builder(this);
        alertAddNoteBuilder = new AlertDialog.Builder(this);
        alertDeleteNoteBuilder = new AlertDialog.Builder(this);

        // Récupérer les items du menu et la vue du menu
        drawerListViewItems = getResources().getStringArray(R.array.menu_items_notes);
        drawerListView = (ListView) findViewById(R.id.left_drawer_notes);

        // Initialisation de l'adapter
        drawerListView.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_listview_item, drawerListViewItems));

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout_notes);

        // Définition de la gestion d'ouverture/fermeture du menu sur glisser du doigt
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close){
            //Menu fermé : gestion du titre de l'activité
            public void onDrawerClosed(View view){
                getSupportActionBar().setTitle(titleBar);
            }
            //Menu ouvert : gestion du titre du menu
            public void onDrawerOpened(View view){
                getSupportActionBar().setTitle(titleMenu);
            }
        };

        // Définition du listener du menu
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        // Définition d'un bouton pour ouvrir le menu
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // Gestion du clic sur un item du menu
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());
    }

    public void init_col_table_notes(){
        col_1 = new ArrayList<String>();
        col_2 = new ArrayList<String>();
        col_3 = new ArrayList<String>();
        col_5 = new ArrayList<String>();
        col_6 = new ArrayList<String>();

        col_1.add("2105478");
        col_1.add("2104789");
        col_1.add("2104525");
        col_1.add("2105236");
        col_1.add("2108792");

        col_2.add("COLAS");
        col_2.add("EL IDRISSI");
        col_2.add("GAUBERT");
        col_2.add("PANNIER");
        col_2.add("SAYABOU");

        col_3.add("Rémi");
        col_3.add("Zakaria");
        col_3.add("Kylroil");
        col_3.add("Cyril");
        col_3.add("Karim");

        col_5.add("12");
        col_5.add("13");
        col_5.add("11");
        col_5.add("12");
        col_5.add("11");

        col_6.add("Examen n°1");
        col_6.add("Examen n°1");
        col_6.add("Examen n°1");
        col_6.add("Examen n°1");
        col_6.add("Examen n°1");
    }

    // Remplit le spinner pour choisir une UE
    private void setContentListeUE() {
        spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_dropdown_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        notes_select_ue.setAdapter(spinnerAdapter);
        notes_select_ue.setBackgroundDrawable(getResources().getDrawable(R.drawable.rectangle));
        spinnerAdapter.add(getResources().getString(R.string.select_ue));
        spinnerAdapter.add("L1 Info - Algorithmique - CM");
        spinnerAdapter.add("L1 Info - Algorithmique - TD");
        spinnerAdapter.add("L3 Info - Programmation Java - TP");
        spinnerAdapter.add("L2 Info - Architecture Système - TP");
        spinnerAdapter.add("L2 Info - Technologies Web - TP");
        spinnerAdapter.notifyDataSetChanged();
    }

    // Remplit le spinner pour choisir une UE
    private void setContentListeStudent() {
        select_student = new Spinner(this);
        spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_dropdown_item_b, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_student.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        select_student.setAdapter(spinnerAdapter);
        select_student.setBackgroundDrawable(getResources().getDrawable(R.drawable.rectangle_b));
        spinnerAdapter.add(getResources().getString(R.string.select_student));
        spinnerAdapter.add("2105478 COLAS Rémi");
        spinnerAdapter.add("2104789 EL IDRISSI Zakaria");
        spinnerAdapter.add("2104525 GAUBERT Kylroil");
        spinnerAdapter.add("2105236 PANNIER Cyril");
        spinnerAdapter.add("2108792 SAYABOU Karim");
        select_student.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parent.startAnimation(anim_clic_on_item);
                select_eleve_confirm = parent.getItemAtPosition(position).toString();
                if (select_eleve_confirm.equals(getResources().getString(R.string.select_student))) {
                    bool_student_not_empty = 0;
                } else {
                    bool_student_not_empty = 1;
                }
                if ((bool_exam_not_empty == 1) && (bool_note_not_empty == 1) && (bool_student_not_empty == 1)){
                    alertAddNote.getButton(AlertDialog.BUTTON1).setEnabled(true);
                }else{
                    alertAddNote.getButton(AlertDialog.BUTTON1).setEnabled(false);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        select_student.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.startAnimation(anim_clic_on_item);
                return false;
            }
        });
        spinnerAdapter.notifyDataSetChanged();
    }


    //Gestion de la sélection des items dans les dropdown list
    private void selection_setListner() {
        notes_select_ue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parent.startAnimation(anim_clic_on_item);
                ue_selected = parent.getItemAtPosition(position).toString();
                table_notes.removeAllViews();
                set_add_button = 0;
                if(position != 0){
                    setTable_notes();
                    set_add_button = 1;
                }
                invalidateOptionsMenu();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        notes_select_ue.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.startAnimation(anim_clic_on_item);
                return false;
            }
        });
    }

    //Affichage des élèves de l'UE sélectionnée
    private void setTable_notes() {

        // Construction du tableau
        for(int i=0;i<col_1.size();i++) {
            final int position = i;
            row_results = new TableRow(this);
            col_num_etudiant = new TextView(this);
            col_num_etudiant.setText(col_1.get(i));
            setStyle(col_num_etudiant);
            col_num_etudiant.setLayoutParams( new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            col_nom_etudiant = new TextView(this);
            col_nom_etudiant.setText(col_2.get(i));
            setStyle(col_nom_etudiant);
            col_nom_etudiant.setLayoutParams( new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            col_prenom_etudiant = new TextView(this);
            col_prenom_etudiant.setText(col_3.get(i));
            setStyle(col_prenom_etudiant);
            col_prenom_etudiant.setLayoutParams( new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            col_note = new TextView(this);
            col_note.setText(col_5.get(i));
            setStyle(col_note);
            col_note.setLayoutParams( new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            col_nom_exam = new TextView(this);
            col_nom_exam.setText(col_6.get(i));
            setStyle(col_nom_exam);
            col_nom_exam.setLayoutParams( new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            // ajout des cellules à la ligne
            row_results.setBackgroundDrawable(getResources().getDrawable(R.drawable.rectangle_c));
            row_results.addView(col_num_etudiant);
            row_results.addView(col_nom_etudiant);
            row_results.addView(col_prenom_etudiant);
//            row_results.addView(col_filiere);
            row_results.addView(col_note);
            row_results.addView(col_nom_exam);

            //Gestion du clic de l'utilisateur sur une ligne de résultat
            row_results.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.startAnimation(anim_clic_on_item);
                    initalertEditNoteBuilder(position);
                    alertEditNote = alertEditNoteBuilder.create();
                    alertEditNote.show();
                    alertEditNote.getButton(AlertDialog.BUTTON1).setEnabled(false);
//                    ((ViewGroup)v.getParent()).removeView(editNote);
                }
            });
            row_results.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    v.startAnimation(anim_clic_on_item);
                    initalertDeleteNoteBuilder(position);
                    alertDeleteNote = alertDeleteNoteBuilder.create();
                    alertDeleteNote.show();
                    return false;
                }
            });

            // ajout de la ligne au tableau
            table_notes.addView(row_results);
        }
    }

    // Définition du style des cellules du tableau
    private void setStyle(TextView cellule) {
        cellule.setGravity(Gravity.CENTER);
        cellule.setTextColor(getResources().getColor(R.color.white));
        cellule.setPadding(4, 4, 4, 4);
        cellule.setTextSize(20);
    }

    //Définition du contenu de la boîte de dialogue de suppression de la note
    private void initalertDeleteNoteBuilder(int i){
        final int position = i;
        alertDeleteNoteBuilder.setTitle(R.string.alertSuppressionNoteTitle);
        String confirm_txt = getResources().getString(R.string.alertSuppressionNoteMessage);
        Log.i("position ----- > ", String.valueOf(position));
        TableRow temp_row = (TableRow)table_notes.getChildAt(position);
        TextView temp_num_etudiant = (TextView)temp_row.getChildAt(index_num_etudiant);
        TextView temp_nom_etudiant = (TextView)temp_row.getChildAt(index_nom_etudiant);
        TextView temp_prenom_etudiant = (TextView) temp_row.getChildAt(index_prenom_etudiant);
        final TextView temp_note_actuelle = (TextView) temp_row.getChildAt(index_note);
        String num_etudiant = (String) temp_num_etudiant.getText();
        String nom_etudiant = (String) temp_nom_etudiant.getText();
        String prenom_etudiant = (String) temp_prenom_etudiant.getText();
        String note_actuelle = (String) temp_note_actuelle.getText();
        alertDeleteNoteBuilder.setMessage(confirm_txt+" "+note_actuelle+" de l'étudiant "+prenom_etudiant+" "+nom_etudiant+" (id: "+num_etudiant+") pour cette matière ?");
        alertDeleteNoteBuilder.setIcon(R.drawable.ic_action_remove);

        alertDeleteNoteBuilder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /* TODO */
                /*Code à définir pour supprimer la note dans la bdd et à prendre en compte dans l'affichage des notes*/
                col_1.remove(position);
                col_2.remove(position);
                col_3.remove(position);
                col_5.remove(position);
                col_6.remove(position);
                table_notes.removeAllViews();
                setTable_notes();
                Toast.makeText(getApplicationContext(), R.string.alertConfirmNoteDeletedToast, Toast.LENGTH_SHORT).show();
            }
        });
        alertDeleteNoteBuilder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }
    
    //Définition du contenu de la boîte de dialogue d'édition de la note
    private void initalertEditNoteBuilder(int i) {
        final int position = i;
        editNote = new EditText(this);
        editNote.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editNote.setHint(getResources().getString(R.string.hint_note));
        editNote.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (editNote.getText().toString().equals(getResources().getString(R.string.vide))){
                    alertEditNote.getButton(AlertDialog.BUTTON1).setEnabled(false);
                }else{
                    alertEditNote.getButton(AlertDialog.BUTTON1).setEnabled(true);
                }
                return false;
            }
        });
        alertEditNoteBuilder.setTitle(R.string.alertModificationNoteTitle);
        alertEditNoteBuilder.setView(editNote);
        String confirm_txt = getResources().getString(R.string.alertModificationNoteMessage);
        TableRow temp_row = (TableRow)table_notes.getChildAt(position);
        TextView temp_num_etudiant = (TextView)temp_row.getChildAt(index_num_etudiant);
        TextView temp_nom_etudiant = (TextView)temp_row.getChildAt(index_nom_etudiant);
        TextView temp_prenom_etudiant = (TextView) temp_row.getChildAt(index_prenom_etudiant);
//        TextView temp_filiere = (TextView) temp_row.getChildAt(index_filiere);
        final TextView temp_note_actuelle = (TextView) temp_row.getChildAt(index_note);
        String num_etudiant = (String) temp_num_etudiant.getText();
        String nom_etudiant = (String) temp_nom_etudiant.getText();
        String prenom_etudiant = (String) temp_prenom_etudiant.getText();
//        String filiere = (String) temp_filiere.getText();
        String note_actuelle = (String) temp_note_actuelle.getText();
        alertEditNoteBuilder.setMessage(confirm_txt+" "+note_actuelle+" de l'étudiant "+prenom_etudiant+" "+nom_etudiant+" (id: "+num_etudiant+") pour cette matière ?");
        alertEditNoteBuilder.setIcon(R.drawable.ic_action_edit);

        alertEditNoteBuilder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /* TODO */
                /*Code à définir pour modifier la note dans la bdd et à prendre en compte dans l'affichage des notes*/
                note_modified = editNote.getText().toString();
                temp_note_actuelle.setText(note_modified);
                Toast.makeText(getApplicationContext(), R.string.alertConfirmNoteModifiedToast, Toast.LENGTH_SHORT).show();
            }
        });
        alertEditNoteBuilder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

    //Définition du contenu de la boîte de dialogue d'ajout de la note
    private void initalertAddNoteBuilder() {
        bool_exam_not_empty = 0;
        bool_student_not_empty = 0;
        bool_note_not_empty = 0;
        setContentListeStudent();
        editNote = new EditText(this);
        editNote.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editNote.setHint(getResources().getString(R.string.hint_note));
        editNote.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (editNote.getText().toString().equals(getResources().getString(R.string.vide))) {
                    bool_note_not_empty = 0;
                } else {
                    bool_note_not_empty = 1;
                }
                if ((bool_exam_not_empty == 1) && (bool_note_not_empty == 1) && (bool_student_not_empty == 1)) {
                    alertAddNote.getButton(AlertDialog.BUTTON1).setEnabled(true);
                } else {
                    alertAddNote.getButton(AlertDialog.BUTTON1).setEnabled(false);
                }
                return false;
            }
        });
        nameExam = new EditText(this);
        nameExam.setHint(getResources().getString(R.string.hint_name_exam));
        nameExam.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        nameExam.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (nameExam.getText().toString().equals(getResources().getString(R.string.vide))) {
                    bool_exam_not_empty = 0;
                } else {
                    bool_exam_not_empty = 1;
                }
                if ((bool_exam_not_empty == 1) && (bool_note_not_empty == 1) && (bool_student_not_empty == 1)) {
                    alertAddNote.getButton(AlertDialog.BUTTON1).setEnabled(true);
                } else {
                    alertAddNote.getButton(AlertDialog.BUTTON1).setEnabled(false);
                }
                return false;
            }
        });
        LinearLayout ll=new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(select_student);
        ll.addView(nameExam);
        ll.addView(editNote);
        alertAddNoteBuilder.setView(ll);
        alertAddNoteBuilder.setTitle(R.string.alertAjoutNoteTitle);
        alertAddNoteBuilder.setMessage(getResources().getString(R.string.alertAjoutNoteMessage));
        alertAddNoteBuilder.setIcon(R.drawable.ic_action_new);

        alertAddNoteBuilder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /* TODO */
                /*Code à définir pour ajouter la note dans la bdd et à prendre en compte dans l'affichage des notes*/
                final String[] temp = select_eleve_confirm.split(" ");
                col_1.add(temp[0]);
                col_2.add(temp[1]);
                col_3.add(temp[2]);
                col_5.add(editNote.getText().toString());
                col_6.add(nameExam.getText().toString());
                table_notes.removeAllViews();
                setTable_notes();
                Toast.makeText(getApplicationContext(), R.string.alertConfirmNoteAddedToast, Toast.LENGTH_SHORT).show();
            }
        });
        alertAddNoteBuilder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_mark, menu);
        if(set_add_button != 0){
            menu.findItem(R.id.action_add_mark).setVisible(true);
        }else{
            menu.findItem(R.id.action_add_mark).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    // Déclenchement du menu sur clic du bouton Home
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()){
            //Déclenchement du bouton Ajouter
            case R.id.action_add_mark:
                initalertAddNoteBuilder();
                alertAddNote = alertAddNoteBuilder.create();
                alertAddNote.show();
                alertAddNote.getButton(AlertDialog.BUTTON1).setEnabled(false);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Afficher l'icône du bouton du menu
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    // Implémentation de la gestion du clic sur un item
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            /* Ce qu'il se passe quand on clique sur un item du menu */
            switch (position){
                case 0:
                    intent = new Intent(Notes.this, Accueil.class);
                    startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(Notes.this, PlanningProf.class);
                    startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(Notes.this, PlanningSalle.class);
                    startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(Notes.this, Reservation.class);
                    startActivity(intent);
                    break;
                case 4:
                    intent = new Intent(Notes.this, Profil.class);
                    startActivity(intent);
                    break;
                case 5:
                    intent = new Intent(Notes.this, Connexion.class);
                    startActivity(intent);
                    /* TODO */
                    /* Définir la déconnexion de l'utilisateur à l'application */
                    Toast.makeText(getApplicationContext(), R.string.boutonCoDeconnexionToast, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
