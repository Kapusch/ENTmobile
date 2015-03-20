package projetandroid.entmobile;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PlanningSalle extends ActionBarActivity implements SearchView.OnQueryTextListener {

    private String[] drawerListViewItems;
    private ListView drawerListView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private CharSequence titleBar;
    private CharSequence titleMenu;
    private Intent intent;
    private TextView jlh8, jlh10, jlh13, jlh15,jlh17, jmh8, jmh10, jmh13, jmh15, jmh17, jmeh8, jmeh10, jmeh13, jmeh15, jmeh17;
    private TextView jjh8, jjh10, jjh13, jjh15, jjh17, jvh8, jvh10, jvh13, jvh15, jvh17, jsh8, jsh10, jsh13, jsh15, jsh17;
    private ImageButton newjlh8, newjlh10, newjlh13, newjlh15, newjlh17, newjmh8, newjmh10, newjmh13, newjmh15, newjmh17, newjmeh8, newjmeh10, newjmeh13, newjmeh15, newjmeh17;
    private ImageButton newjjh8, newjjh10, newjjh13, newjjh15, newjjh17, newjvh8, newjvh10, newjvh13, newjvh15, newjvh17, newjsh8, newjsh10, newjsh13, newjsh15, newjsh17;
    private Context context;
    private Animation clique;
    private GridLayout planning;
    private Calendar semaine_actu;
    private Calendar date;
    private int jour;
    private int mois;
    private int annee;
    private AlertDialog alertModifyPlanning;
    private AlertDialog.Builder alertModifyPlanningBuilder;

    private GridLayout tab_planning;
    private Spinner select_ue;
    private ArrayAdapter<String> spinnerAdapter;
    private String ue_toast_confirm;
    private SearchView mSearchView;

    protected void onCreate(Bundle savedInstanceState){
        //Définition de la searchBar
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning_salle);

        context = this;

        //Définition des animations
        clique = AnimationUtils.loadAnimation(this, R.anim.fadein);

        //Définition du listener des cliques sur les cases
        initAnim_clique();

        initPlanning();

        //Définition de la boîte de dialogue de confirmation de la réservation de la salle
        alertModifyPlanningBuilder = new AlertDialog.Builder(this);

        //Définition de la semaine en cours
        semaine_actu = semaine_actu.getInstance();
        setSemaineActu();

        //Gestion du bouton d'action pour sélectionner une date
        setDatePickerDialog();

        //Définition des titres
        titleBar = getResources().getString(R.string.label_planning_salle);
        titleMenu = getResources().getString(R.string.label_menu);

        // Récupérer les items du menu et la vue du menu
        drawerListViewItems = getResources().getStringArray(R.array.menu_items_planning_salle);
        drawerListView = (ListView) findViewById(R.id.left_drawer_planning_salle);

        // Initialisation de l'adapter
        drawerListView.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_listview_item, drawerListViewItems));

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout_planning_salle);

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


    //Définition des éléments de la page
    private void setElementsFromLayout() {
        jlh8 = (TextView)findViewById(R.id.jlh8);
        jlh10 = (TextView)findViewById(R.id.jlh10);
        jlh13 = (TextView)findViewById(R.id.jlh13);
        jlh15 = (TextView)findViewById(R.id.jlh15);
        jlh17 = (TextView)findViewById(R.id.jlh17);
        jmh8 = (TextView)findViewById(R.id.jmh8);
        jmh10 = (TextView)findViewById(R.id.jmh10);
        jmh13 = (TextView)findViewById(R.id.jmh13);
        jmh15 = (TextView)findViewById(R.id.jmh15);
        jmh17 = (TextView)findViewById(R.id.jmh17);
        jmeh8 = (TextView)findViewById(R.id.jmeh8);
        jmeh10 = (TextView)findViewById(R.id.jmeh10);
        jmeh13 = (TextView)findViewById(R.id.jmeh13);
        jmeh15 = (TextView)findViewById(R.id.jmeh15);
        jmeh17 = (TextView)findViewById(R.id.jmeh17);
        jjh8 = (TextView)findViewById(R.id.jjh8);
        jjh10 = (TextView)findViewById(R.id.jjh10);
        jjh13 = (TextView)findViewById(R.id.jjh13);
        jjh15 = (TextView)findViewById(R.id.jjh15);
        jjh17 = (TextView)findViewById(R.id.jjh17);
        jvh8 = (TextView)findViewById(R.id.jvh8);
        jvh10 = (TextView)findViewById(R.id.jvh10);
        jvh13 = (TextView)findViewById(R.id.jvh13);
        jvh15 = (TextView)findViewById(R.id.jvh15);
        jvh17 = (TextView)findViewById(R.id.jvh17);
        jsh8 = (TextView)findViewById(R.id.jsh8);
        jsh10 = (TextView)findViewById(R.id.jsh10);
        jsh13 = (TextView)findViewById(R.id.jsh13);
        jsh15 = (TextView)findViewById(R.id.jsh15);
        jsh17 = (TextView)findViewById(R.id.jsh17);

        newjlh8 = (ImageButton)findViewById(R.id.newjlh8);
        newjlh10 = (ImageButton)findViewById(R.id.newjlh10);
        newjlh13 = (ImageButton)findViewById(R.id.newjlh13);
        newjlh15 = (ImageButton)findViewById(R.id.newjlh15);
        newjlh17 = (ImageButton)findViewById(R.id.newjlh17);
        newjmh8 = (ImageButton)findViewById(R.id.newjmh8);
        newjmh10 = (ImageButton)findViewById(R.id.newjmh10);
        newjmh13 = (ImageButton)findViewById(R.id.newjmh13);
        newjmh15 = (ImageButton)findViewById(R.id.newjmh15);
        newjmh17 = (ImageButton)findViewById(R.id.newjmh17);
        newjmeh8 = (ImageButton)findViewById(R.id.newjmeh8);
        newjmeh10 = (ImageButton)findViewById(R.id.newjmeh10);
        newjmeh13 = (ImageButton)findViewById(R.id.newjmeh13);
        newjmeh15 = (ImageButton)findViewById(R.id.newjmeh15);
        newjmeh17 = (ImageButton)findViewById(R.id.newjmeh17);
        newjjh8 = (ImageButton)findViewById(R.id.newjjh8);
        newjjh10 = (ImageButton)findViewById(R.id.newjjh10);
        newjjh13 = (ImageButton)findViewById(R.id.newjjh13);
        newjjh15 = (ImageButton)findViewById(R.id.newjjh15);
        newjjh17 = (ImageButton)findViewById(R.id.newjjh17);
        newjvh8 = (ImageButton)findViewById(R.id.newjvh8);
        newjvh10 = (ImageButton)findViewById(R.id.newjvh10);
        newjvh13 = (ImageButton)findViewById(R.id.newjvh13);
        newjvh15 = (ImageButton)findViewById(R.id.newjvh15);
        newjvh17 = (ImageButton)findViewById(R.id.newjvh17);
        newjsh8 = (ImageButton)findViewById(R.id.newjsh8);
        newjsh10 = (ImageButton)findViewById(R.id.newjsh10);
        newjsh13 = (ImageButton)findViewById(R.id.newjsh13);
        newjsh15 = (ImageButton)findViewById(R.id.newjsh15);
        newjsh17 = (ImageButton)findViewById(R.id.newjsh17);
    }


    //Initialisation des éléments du planning
    private void initPlanning() {
//        int max = tab_planning.getChildCount();
        tab_planning = (GridLayout)findViewById(R.id.cellule);
        setElementsFromLayout();
        int i = 0, j, endRow = 8, rowCount = 0;
        ((TextView) tab_planning.getChildAt(6)).setText("M1-Info Processus stochastiques et heuristiques (CM) -- Amphi 9001 Dpt. Physique");
        ((TextView) tab_planning.getChildAt(12)).setText("L3-Info Projet d'étude de cas tutoré (TP) -- Amphi 9001 Dpt. Physique");
        ((TextView) tab_planning.getChildAt(18)).setText("L3-Info Projet d'étude de cas tutoré (TP) -- Amphi 9001 Dpt. Physique");
//        Toast.makeText(getApplicationContext(), String.valueOf(max), Toast.LENGTH_SHORT).show();
        while (rowCount < 6) {
            for (j = i; j <= endRow; j += 2) {
                final TextView cellule_temp = (TextView) tab_planning.getChildAt(j);
                final int finalJ = j;
                final String salle_txt = "Amphi 9001 Dpt. Physique";
                if ((cellule_temp.getText().equals(getResources().getString(R.string.vide)))) {
                    cellule_temp.setClickable(true);
                    final ImageButton new_button_temp = (ImageButton) tab_planning.getChildAt(j + 1);
                    new_button_temp.setVisibility(View.VISIBLE);
                    new_button_temp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new_button_temp.startAnimation(clique);
                            initalertModifyPlanningBuilder(salle_txt, finalJ);
                            alertModifyPlanning = alertModifyPlanningBuilder.create();
                            alertModifyPlanning.show();
                        }
                    });
                }else{
                    cellule_temp.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            Toast.makeText(getApplicationContext(), cellule_temp.getText().toString(), Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    });
                }
            }
            i = j;
            endRow = i + 8;
            rowCount++;
        }
    }

    //Création de la boîte de dialogue pour supprimer ou déplacer un cours
    private void initalertModifyPlanningBuilder(final String salle_txt, final int position) {
        setContentListeUE();
        alertModifyPlanningBuilder.setView(select_ue);
        String confirm_txt = getResources().getString(R.string.alertAjoutPlanningMessage_start).concat(" "+salle_txt).concat(getResources().getString(R.string.alertAjoutPlanningMessage_end));
        alertModifyPlanningBuilder.setTitle((R.string.alertAjoutPlanningTitle));
        alertModifyPlanningBuilder.setMessage(confirm_txt);
        alertModifyPlanningBuilder.setIcon(R.drawable.ic_action_new);
        alertModifyPlanningBuilder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /* TODO */
                /*Code à définir pour prendre en compte la réservation de la salle dans la BDD*/
                final TextView new_cell = (TextView)tab_planning.getChildAt(position);
                final ImageButton new_button_temp = (ImageButton) tab_planning.getChildAt(position + 1);
                new_button_temp.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), R.string.alertConfirmPlanningModifiedToast, Toast.LENGTH_SHORT).show();
                //On modifie l'affichage du planning modifié
                new_cell.setText(ue_toast_confirm.concat(" -- " + salle_txt));
                new_cell.setClickable(false);
                new_cell.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(getApplicationContext(), new_cell.getText().toString(), Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
            }
        });
        alertModifyPlanningBuilder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

    //Récupération et édit de la semaine en cours
    private void setSemaineActu() {
        //Récupération de la semaine
        planning = (GridLayout)findViewById(R.id.tableau1);
        int week_actu_number = semaine_actu.get(Calendar.WEEK_OF_YEAR);
        int year_actu_number = semaine_actu.get(Calendar.YEAR);
        String[] days = {getResources().getString(R.string.lundi), getResources().getString(R.string.mardi), getResources().getString(R.string.mercredi), getResources().getString(R.string.jeudi), getResources().getString(R.string.vendredi), getResources().getString(R.string.samedi)};
        TextView cell_temp = (TextView)planning.getChildAt(0);
        String date_temp = "Semaine n°"+String.valueOf(semaine_actu.get(Calendar.WEEK_OF_YEAR));
        cell_temp.setText(date_temp);

        //Récupération des jours de la semaine
        semaine_actu.clear();
        semaine_actu.set(Calendar.WEEK_OF_YEAR, week_actu_number);
        semaine_actu.set(Calendar.YEAR, year_actu_number);
        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        Date startDate;
        SimpleDateFormat month_format = new SimpleDateFormat("MMMM");
        String month_name, startDateText;
        int startDateInt;
        for(int i = 1; i < 7; i++){
            cell_temp = (TextView) planning.getChildAt(i);
            startDate = semaine_actu.getTime();
            startDateText = formatter.format(startDate);
            startDateInt = Integer.valueOf(startDateText);
            month_name = month_format.format(semaine_actu.getTime());
            date_temp = days[(i-1)].concat(" "+startDateInt).concat(" "+month_name);
            cell_temp.setText(date_temp);
            semaine_actu.add(Calendar.DATE, 1);
        }
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
            semaine_actu.clear();
            semaine_actu.set(selectedYear, selectedMonth, selectedDay);
            setSemaineActu();
        }
    };

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, annee, mois, jour);
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
            //Déclenchement du bouton Calendrier
            case R.id.action_select_date:
                showDialog(0);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Remplit le spinner pour choisir une UE
    private void setContentListeUE() {
        select_ue = new Spinner(this);
        spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_dropdown_item_b, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_ue.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        select_ue.setAdapter(spinnerAdapter);
        select_ue.setBackgroundDrawable(getResources().getDrawable(R.drawable.rectangle_b));
        spinnerAdapter.add(getResources().getString(R.string.select_ue));
        /*TODO*/
        //Ici le code pour récupérer la liste des UE du prof connecté.
        spinnerAdapter.add("L1 Info - Algorithmique - CM");
        spinnerAdapter.add("L1 Info - Algorithmique - TD");
        spinnerAdapter.add("L3 Info - Programmation Java - TP");
        spinnerAdapter.add("L2 Info - Architecture Système - TP");
        spinnerAdapter.add("L2 Info - Technologies Web - TP");
        select_ue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parent.startAnimation(clique);
                ue_toast_confirm = parent.getItemAtPosition(position).toString();
                if(ue_toast_confirm.equals(getResources().getString(R.string.select_ue))){
                    alertModifyPlanning.getButton(AlertDialog.BUTTON1).setEnabled(false);
                }else {
                    alertModifyPlanning.getButton(AlertDialog.BUTTON1).setEnabled(true);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        select_ue.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.startAnimation(clique);
                return false;
            }
        });
        spinnerAdapter.notifyDataSetChanged();
    }

    // ******************************
    //Gestion de la barre de recherche
    // ******************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_select_date_searchview, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchItem.getActionView();
        setupSearchView(searchItem);

        return true;
    }

    private void setupSearchView(MenuItem searchItem) {
        //Définit un autre aspect de la barre de recherche
//        searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();

            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
            for (SearchableInfo inf : searchables) {
                if (inf.getSuggestAuthority() != null
                        && inf.getSuggestAuthority().startsWith("contacts")) {
                    info = inf;
                }
            }
            mSearchView.setSearchableInfo(info);
        }

        mSearchView.setOnQueryTextListener(this);
    }

    //TODO
    /*Code à redéfinir pour gérer la requête dans la bdd*/
    @Override
    public boolean onQueryTextSubmit(String query) {
//        mStatusView.setText("Query = " + query + " : submitted");
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
//        mStatusView.setText("Query = " + newText);
        return false;
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
                    intent = new Intent(PlanningSalle.this, Accueil.class);
                    startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(PlanningSalle.this, PlanningProf.class);
                    startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(PlanningSalle.this, Reservation.class);
                    startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(PlanningSalle.this, Notes.class);
                    startActivity(intent);
                    break;
                case 4:
                    intent = new Intent(PlanningSalle.this, Profil.class);
                    startActivity(intent);
                    break;
                case 5:
                    intent = new Intent(PlanningSalle.this, Connexion.class);
                    startActivity(intent);
                    /* TODO */
                    /* Définir la déconnexion de l'utilisateur à l'application */
                    Toast.makeText(getApplicationContext(), R.string.boutonCoDeconnexionToast, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }


    private void initAnim_clique() {
        clique.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
