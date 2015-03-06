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
import android.text.InputType;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Notes extends ActionBarActivity{

    private String[] drawerListViewItems;
    private ListView drawerListView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private CharSequence titleBar;
    private CharSequence titleMenu;
    private Intent intent;
    private Spinner notes_select_ue;
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
    private ArrayAdapter<String> spinnerAdapter;
    private TableRow row_results;
    private TextView col_num_etudiant;
    private TextView col_nom_etudiant;
    private TextView col_prenom_etudiant;
    private TextView col_filiere;
    private TextView col_note;
    private AlertDialog alertEditNote;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        //Définition des titres
        titleBar = getResources().getString(R.string.label_notes);
        titleMenu = getResources().getString(R.string.label_menu);

        //Définition des éléments de la page
        notes_select_ue = (Spinner)findViewById(R.id.notes_select_ue);
        table_notes = (TableLayout)findViewById(R.id.table_notes);
        setContentListeUE();

        //Définition des animations
        anim_clic_on_item = AnimationUtils.loadAnimation(this, R.anim.fadein);

        //Définition des listners
        selection_setListner();

        //Définition de la boîte de dialogue de confirmation de modification de la note
        alertEditNoteBuilder = new AlertDialog.Builder(this);

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


    //Gestion de la sélection des items dans les dropdown list
    private void selection_setListner() {
        notes_select_ue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parent.startAnimation(anim_clic_on_item);
                ue_selected = parent.getItemAtPosition(position).toString();
                table_notes.removeAllViews();
                if(position != 0){
                    setTable_notes();
                }
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
        final String [] col_1 = {"2105478", "2104789", "2104525", "2105236", "2108792"};
        final String [] col_2 = {"COLAS", "EL IDRISSI", "GAUBERT", "PANNIER", "SAYABOU"};
        final String [] col_3 = {"Rémi", "Zakaria", "Kylroil", "Cyril", "Karim"};
        final String [] col_4 = {"L3 Info", "L3 Info", "L3 Info", "L3 Info", "L3 Info"};
        final String [] col_5 = {"12", "13", "11", "12", "11"};

        // Construction du tableau
        for(int i=0;i<col_1.length;i++) {
            final int position = i;
            row_results = new TableRow(this);
            col_num_etudiant = new TextView(this);
            col_num_etudiant.setText(col_1[i]);
            setStyle(col_num_etudiant);
            col_num_etudiant.setLayoutParams( new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            col_nom_etudiant = new TextView(this);
            col_nom_etudiant.setText(col_2[i]);
            setStyle(col_nom_etudiant);
            col_nom_etudiant.setLayoutParams( new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            col_prenom_etudiant = new TextView(this);
            col_prenom_etudiant.setText(col_3[i]);
            setStyle(col_prenom_etudiant);
            col_prenom_etudiant.setLayoutParams( new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            col_filiere = new TextView(this);
            col_filiere.setText(col_4[i]);
            setStyle(col_filiere);
            col_filiere.setLayoutParams( new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            col_note = new TextView(this);
            col_note.setText(col_5[i]);
            setStyle(col_note);
            col_note.setLayoutParams( new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            // ajout des cellules à la ligne
            row_results.setBackgroundDrawable(getResources().getDrawable(R.drawable.rectangle_c));
            row_results.addView(col_num_etudiant);
            row_results.addView(col_nom_etudiant);
            row_results.addView(col_prenom_etudiant);
            row_results.addView(col_note);

            //Gestion du clic de l'utilisateur sur une ligne de résultat
            row_results.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    table_notes.getChildAt(position).startAnimation(anim_clic_on_item);
                    initalertEditNoteBuilder(position);
                    alertEditNote = alertEditNoteBuilder.create();
                    alertEditNote.show();
                    ((ViewGroup)v.getParent()).removeView(editNote);
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
        cellule.setTextSize(24);
    }
    
    //Définition du contenu de la boîte de dialogue
    private void initalertEditNoteBuilder(int i) {
        final int position = i;
        editNote = new EditText(this);
        editNote.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
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
