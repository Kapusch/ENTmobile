package projetandroid.entmobile;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Accueil extends ActionBarActivity{

    private String[] drawerListViewItems;
    private ListView drawerListView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private CharSequence titleBar;
    private CharSequence titleMenu;
    Intent intent;
    private GridLayout planning;
    private Calendar semaine_actu;
    private Calendar date;
    private int jour;
    private int mois;
    private int annee;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        //Définition de la semaine en cours
        semaine_actu = semaine_actu.getInstance();
        setSemaineActu();

        //Gestion du bouton d'action pour sélectionner une date
        setDatePickerDialog();

        //Définition des titres
        titleBar = getResources().getString(R.string.label_accueil);
        titleMenu = getResources().getString(R.string.label_menu);

        // Récupérer les items du menu et la vue du menu
        drawerListViewItems = getResources().getStringArray(R.array.menu_items_accueil);
        drawerListView = (ListView) findViewById(R.id.left_drawer_accueil);

        // Initialisation de l'adapter
        drawerListView.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_listview_item, drawerListViewItems));

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout_accueil);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_select_date, menu);
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
            //Déclenchement du bouton Calendrier
            case R.id.action_select_date:
                showDialog(0);
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
                    intent = new Intent(Accueil.this, PlanningProf.class);
                    startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(Accueil.this, PlanningSalle.class);
                    startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(Accueil.this, Reservation.class);
                    startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(Accueil.this, Notes.class);
                    startActivity(intent);
                    break;
                case 4:
                    intent = new Intent(Accueil.this, Profil.class);
                    startActivity(intent);
                    break;
                case 5:
                    intent = new Intent(Accueil.this, Connexion.class);
                    startActivity(intent);
                    /* TODO */
                    /* Définir la déconnexion de l'utilisateur à l'application */
                    Toast.makeText(getApplicationContext(), R.string.boutonCoDeconnexionToast, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
