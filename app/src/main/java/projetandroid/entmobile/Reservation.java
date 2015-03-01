package projetandroid.entmobile;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Reservation extends ActionBarActivity implements View.OnClickListener{

    private String[] drawerListViewItems;
    private ListView drawerListView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private CharSequence titleBar;
    private CharSequence titleMenu;
    private Intent intent;
    private Button boutonRecherche_salle;
    private Button boutonRecherche_salle_new;
    private Animation anim_panelRecherche_salle_show;
    private Animation anim_panelRecherche_salle_hide;
    private Animation anim_panelRecherche_salle_results_show;
    private Animation anim_panelRecherche_salle_results_hide;
    private LinearLayout panelRecherche_salle;
    private LinearLayout panelRecherche_salle_results;
    private NumberPicker numberPicker_effectif;
    private TextView effectif_text;
    private NumberPicker numberPicker_duree_cours;
    private TextView duree_cours_text;
    private int jour;
    private int mois;
    private int annee;
    private TextView date_text;
    private ImageButton calendar;
    private Calendar date;
    private Animation anim_boutonCalendar;
    private Button boutonReinitialize;
    private Spinner type_salle;
    private String salle;
    private Spinner nom_departement;
    private String departement;
    private Spinner nom_equipement_a;
    private String equipement_a;
    private Spinner nom_equipement_b;
    private String equipement_b;
    private Spinner horaire_debut_cours;
    private String debut_cours;
    private int duree_cours;
    private int effectif;
    private MatrixCursor matrixCursor;
    private AlertDialog.Builder alertConfirmBuilder;
    private AlertDialog alertConfirm;
    private ListView lv_results;
    private TextView lv_column_nom_salle;
    private TextView lv_column_departement;
    private TextView lv_column_capacite;
    private TextView lv_column_horaire;
    private LinearLayout lv_row;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        //Définition des titres
        titleBar = getResources().getString(R.string.label_reservation);
        titleMenu = getResources().getString(R.string.label_menu);

        //Définition des éléments de la page
        boutonRecherche_salle = (Button)findViewById(R.id.boutonRecherche_salle);
        boutonRecherche_salle_new = (Button)findViewById(R.id.boutonRecherche_salle_new);
        boutonReinitialize = (Button)findViewById(R.id.boutonReinitialize);
        panelRecherche_salle = (LinearLayout)findViewById(R.id.panel_recherche_salle);
        panelRecherche_salle_results = (LinearLayout)findViewById(R.id.panel_recherche_salle_results);
        effectif_text = (TextView)findViewById(R.id.effectif_recherche);
        numberPicker_effectif = (NumberPicker)findViewById(R.id.effectif_recherche_number_picker);
        numberPicker_duree_cours = (NumberPicker)findViewById(R.id.duree_cours_recherche_number_picker);
        duree_cours_text = (TextView)findViewById(R.id.duree_cours);
        date_text = (TextView)findViewById(R.id.date_recheche);
        calendar = (ImageButton)findViewById(R.id.date_recherche_calendar);
        type_salle = (Spinner)findViewById(R.id.recheche_type_salle);
        nom_departement = (Spinner)findViewById(R.id.recherche_nom_departement);
        nom_equipement_a = (Spinner)findViewById(R.id.nom_equipement_a);
        nom_equipement_b = (Spinner)findViewById(R.id.nom_equipement_b);
        horaire_debut_cours = (Spinner)findViewById(R.id.horaire_debut_cours);
        lv_results = (ListView)findViewById(R.id.listview_recherche_results);
        lv_column_nom_salle = (TextView)findViewById(R.id.colonne_nom_salle);
        lv_column_departement = (TextView)findViewById(R.id.colonne_departement);
        lv_column_capacite = (TextView)findViewById(R.id.colonne_capacite);
        lv_column_horaire = (TextView)findViewById(R.id.colonne_horaire);
        lv_row = (LinearLayout)findViewById(R.id.row_recherche_results);


        //Définition des éléments de sélection
        setDatePickerDialog();
        setNumberPicker();

        //Définition de la boîte de dialogue de confirmation de réservation
        alertConfirmBuilder = new AlertDialog.Builder(this);
        initalertConfirmBuilder();
        alertConfirm = alertConfirmBuilder.create();

        //Définition des animations
        anim_panelRecherche_salle_show = AnimationUtils.loadAnimation(this, R.anim.push_left_in);
        anim_panelRecherche_salle_hide = AnimationUtils.loadAnimation(this, R.anim.push_right_out);
        anim_panelRecherche_salle_results_show = AnimationUtils.loadAnimation(this, R.anim.push_right_in);
        anim_panelRecherche_salle_results_hide = AnimationUtils.loadAnimation(this, R.anim.push_left_out);
        anim_boutonCalendar = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);

        //Définition des listeners
        boutonRecherche_salle.setOnClickListener(this);
        boutonRecherche_salle_new.setOnClickListener(this);
        boutonReinitialize.setOnClickListener(this);
        calendar.setOnClickListener(this);
        selection_setListner();
        initAnim_boutonCalendar();

        // Récupérer les items du menu et la vue du menu
        drawerListViewItems = getResources().getStringArray(R.array.menu_items_reservation);
        drawerListView = (ListView) findViewById(R.id.left_drawer_reservation);

        // Initialisation de l'adapter
        drawerListView.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_listview_item, drawerListViewItems));

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout_reservation);

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

    //Définition de l'affichage des résultats de la recherche
    private void setListViewResults() {
        // Définition des colonnes du tableau des résultats de la recherche
        String[] columns = new String[] { "_id", "nom_salle", "departement", "capacite", "horaire" };
        matrixCursor = new MatrixCursor(columns);
        startManagingCursor(matrixCursor);
        matrixCursor.addRow(new Object[] { 1,"SC 3005","Maths-info", "18", "09:00" });
        matrixCursor.addRow(new Object[] { 2,"SC 3002","Maths-info", "20", "13:30" });

        // Prendre les données de toutes les colonnes
        String[] from = new String[] {"nom_salle", "departement", "capacite", "horaire"};

        // Placer chaque donnée dans les colonnes respectives
        int[] to = new int[] { R.id.colonne_nom_salle, R.id.colonne_departement, R.id.colonne_capacite, R.id.colonne_horaire};

        // Définition de l'adapter pour remplir le tableau avec les résultats
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.row_listview, matrixCursor, from, to, 0);
        lv_results = (ListView) findViewById(R.id.listview_recherche_results);
        lv_results.setAdapter(adapter);
        // Gestion des clics sur les lignes
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                //Gestion du clic de l'utilisateur sur une ligne de résultat
                alertConfirm.show();
            }
        };

    // Utilisation avec notre listview
        lv_results.setOnItemClickListener(itemClickListener);
    }

    //Définition du contenu de la boîte de dialogue
    private void initalertConfirmBuilder() {
        alertConfirmBuilder.setTitle(R.string.alertReservationTitle);
        String confirm_txt = getResources().getString(R.string.alertReservationMessage);
        String nom_date = jour+"-"+(mois+1)+"-"+annee;
        alertConfirmBuilder.setMessage("Confirmez-vous la réservation de la salle SC3005 le "+nom_date+" à 09:00 ?");
        alertConfirmBuilder.setIcon(R.drawable.ic_action_time);

        alertConfirmBuilder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /* TODO */
                /*Code à définir pour réserver une salle dans la bdd*/
                reinitialize();
                panelRecherche_salle_results.startAnimation(anim_panelRecherche_salle_results_hide);
                panelRecherche_salle_results.setVisibility(View.INVISIBLE);
                panelRecherche_salle.startAnimation(anim_panelRecherche_salle_show);
                panelRecherche_salle.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), R.string.alertConfirmToast, Toast.LENGTH_SHORT).show();
            }
        });
        alertConfirmBuilder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

    //Gestion de la sélection des items dans les dropdown list
    private void selection_setListner() {
        type_salle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                salle = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        nom_departement.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                departement = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        nom_equipement_a.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                equipement_a = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        nom_equipement_b.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                equipement_b = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        horaire_debut_cours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                debut_cours = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //Gestion de la sélection de la date pour le cours
    private void setDatePickerDialog() {
        date = Calendar.getInstance();
        jour = date.get(Calendar.DAY_OF_MONTH);
        mois = date.get(Calendar.MONTH);
        annee = date.get(Calendar.YEAR);
    }

    //Modification de la date après sélection
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            date_text.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                    + selectedYear);
        }
    };

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, annee, mois, jour);
    }

    //Gestion du number picker
    private void setNumberPicker() {
        numberPicker_effectif.setMinValue(0);
        numberPicker_effectif.setMaxValue(300);
        numberPicker_effectif.setWrapSelectorWheel(false);
        numberPicker_effectif.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                String Old = getResources().getString(R.string.recherche_effectif_text);
                effectif = newVal;
                effectif_text.setText(Old.concat(" "+String.valueOf(newVal)));
            }
        });
        numberPicker_duree_cours.setMinValue(1);
        numberPicker_duree_cours.setMaxValue(10);
        numberPicker_duree_cours.setWrapSelectorWheel(false);
        numberPicker_duree_cours.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                String Old = getResources().getString(R.string.recherche_duree_cours_text);
                duree_cours = newVal;
                duree_cours_text.setText(Old.concat(" "+String.valueOf(newVal)));
            }
        });
    }

    //Réinitialiser le formulaire
    private void reinitialize() {
        numberPicker_effectif.setValue(0);
        effectif_text.setText(getResources().getString(R.string.recherche_effectif_text));
        date_text.setText(getResources().getString(R.string.recherche_date_text));
        removeDialog(0);
        type_salle.setSelection(0);
        nom_departement.setSelection(0);
        nom_equipement_a.setSelection(0);
        nom_equipement_b.setSelection(0);
        numberPicker_duree_cours.setValue(1);
        duree_cours_text.setText(getResources().getString(R.string.recherche_duree_cours_text));
        horaire_debut_cours.setSelection(0);
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
                    intent = new Intent(Reservation.this, Accueil.class);
                    startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(Reservation.this, PlanningProf.class);
                    startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(Reservation.this, PlanningSalle.class);
                    startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(Reservation.this, Notes.class);
                    startActivity(intent);
                    break;
                case 4:
                    intent = new Intent(Reservation.this, Profil.class);
                    startActivity(intent);
                    break;
                case 5:
                    intent = new Intent(Reservation.this, Connexion.class);
                    startActivity(intent);
                    /* TODO */
                    /* Définir la déconnexion de l'utilisateur à l'application */
                    Toast.makeText(getApplicationContext(), R.string.boutonCoDeconnexionToast, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    //Gestion des évènements sur clic des boutons
    /* TODO */
    /*Code pour effectuer une recherche dans la BDD pour une réservation de salle*/
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.boutonRecherche_salle:
                setListViewResults();
                panelRecherche_salle.startAnimation(anim_panelRecherche_salle_hide);
                panelRecherche_salle.setVisibility(View.INVISIBLE);
                panelRecherche_salle_results.startAnimation(anim_panelRecherche_salle_results_show);
                panelRecherche_salle_results.setVisibility(View.VISIBLE);
                //Code pour la requête SQL
                break;

            case R.id.boutonRecherche_salle_new:
                reinitialize();
                panelRecherche_salle_results.startAnimation(anim_panelRecherche_salle_results_hide);
                panelRecherche_salle_results.setVisibility(View.INVISIBLE);
                panelRecherche_salle.startAnimation(anim_panelRecherche_salle_show);
                panelRecherche_salle.setVisibility(View.VISIBLE);
                break;

            case R.id.boutonReinitialize:
                reinitialize();
                break;

            case R.id.date_recherche_calendar:
                calendar.startAnimation(anim_boutonCalendar);
                break;
        }
    }

    //Définition du listener de l'animation du bouton du Calendrier
    private void initAnim_boutonCalendar() {
        anim_boutonCalendar.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                showDialog(0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
