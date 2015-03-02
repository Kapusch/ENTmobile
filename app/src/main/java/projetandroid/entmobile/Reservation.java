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
import android.view.Gravity;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

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
    private AlertDialog.Builder alertConfirmBuilder;
    private AlertDialog alertConfirm;
    private ListView lv_results;
    private TableLayout table_results;
    private TableRow row_results;
    private TextView col_nom_salle;
    private TextView col_departement;
    private TextView col_capacite;
    private TextView col_horaire;
    private int index_nom_salle = 0;
    private int index_departement = 1;
    private int index_capacite = 2;
    private int index_horaire = 3;
    private Animation anim_clic_on_item;

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
        table_results = (TableLayout)findViewById(R.id.table_recherche_results);


        //Définition des éléments de sélection
        setDatePickerDialog();
        setNumberPicker();

        //Définition des animations
        anim_panelRecherche_salle_show = AnimationUtils.loadAnimation(this, R.anim.push_left_in);
        anim_panelRecherche_salle_hide = AnimationUtils.loadAnimation(this, R.anim.push_right_out);
        anim_panelRecherche_salle_results_show = AnimationUtils.loadAnimation(this, R.anim.push_right_in);
        anim_panelRecherche_salle_results_hide = AnimationUtils.loadAnimation(this, R.anim.push_left_out);
        anim_boutonCalendar = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        anim_clic_on_item = AnimationUtils.loadAnimation(this, R.anim.fadein);

        //Définition des listeners
        boutonRecherche_salle.setOnClickListener(this);
        boutonRecherche_salle_new.setOnClickListener(this);
        boutonReinitialize.setOnClickListener(this);
        calendar.setOnClickListener(this);
        selection_setListner();
        initAnim_boutonCalendar();

        //Définition de la boîte de dialogue de confirmation de réservation
        alertConfirmBuilder = new AlertDialog.Builder(this);

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

    //Affichage des résultats
    private void setTableResults() {
        final String [] col_1 = {"SC_3005", "SC_3002"};
        final String [] col_2 = {"Maths-info", "Maths-info"};
        final String [] col_3 = {"18", "20"};
        final String [] col_4 = {"09:00", "13:30"};

        // Construction du tableau
        for(int i=0;i<col_1.length;i++) {
            final int position = i;
            row_results = new TableRow(this);
            col_nom_salle = new TextView(this);
            col_nom_salle.setText(col_1[i]);
            setStyle(col_nom_salle);
            col_nom_salle.setLayoutParams( new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            col_departement = new TextView(this);
            col_departement.setText(col_2[i]);
            setStyle(col_departement);
            col_departement.setLayoutParams( new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            col_capacite = new TextView(this);
            col_capacite.setText(col_3[i]);
            setStyle(col_capacite);
            col_capacite.setLayoutParams( new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            col_horaire = new TextView(this);
            col_horaire.setText(col_4[i]);
            setStyle(col_horaire);
            col_horaire.setLayoutParams( new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            // ajout des cellules à la ligne
            row_results.addView(col_nom_salle);
            row_results.addView(col_departement);
            row_results.addView(col_capacite);
            row_results.addView(col_horaire);

            //Gestion du clic de l'utilisateur sur une ligne de résultat
            row_results.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    table_results.getChildAt(position).startAnimation(anim_clic_on_item);
                    initalertConfirmBuilder(position);
                    alertConfirm = alertConfirmBuilder.create();
                    alertConfirm.show();
                }
            });

            // ajout de la ligne au tableau
            table_results.addView(row_results);
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
    private void initalertConfirmBuilder(int i) {
        alertConfirmBuilder.setTitle(R.string.alertReservationTitle);
        String confirm_txt = getResources().getString(R.string.alertReservationMessage);
        TableRow temp_row = (TableRow)table_results.getChildAt(i);
        TextView temp_salle = (TextView)temp_row.getChildAt(index_nom_salle);
        TextView temp_horaire = (TextView)temp_row.getChildAt(index_horaire);
        TextView temp_departement = (TextView) temp_row.getChildAt(index_departement);
        String nom_salle = (String) temp_salle.getText();
        String horaire = (String) temp_horaire.getText();
        String departement = (String) temp_departement.getText();
        String nom_date = jour+"-"+(mois+1)+"-"+annee;
        alertConfirmBuilder.setMessage(confirm_txt+" "+nom_salle+" du département "+departement+" le "+nom_date+" à "+horaire+" ?");

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
        table_results.removeAllViews();
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
                setTableResults();
//                initalertConfirmBuilder(i);
//                alertConfirm = alertConfirmBuilder.create();
                panelRecherche_salle.startAnimation(anim_panelRecherche_salle_hide);
                panelRecherche_salle.setVisibility(View.INVISIBLE);
                panelRecherche_salle_results.startAnimation(anim_panelRecherche_salle_results_show);
                panelRecherche_salle_results.setVisibility(View.VISIBLE);
                //Code pour la requête SQL
                break;

            case R.id.boutonRecherche_salle_new:
                panelRecherche_salle_results.startAnimation(anim_panelRecherche_salle_results_hide);
                panelRecherche_salle_results.setVisibility(View.INVISIBLE);
                reinitialize();
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
