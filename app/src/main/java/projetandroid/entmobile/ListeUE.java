package projetandroid.entmobile;

import android.app.ActionBar;
import android.app.AlertDialog;
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
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ListeUE extends ActionBarActivity implements SearchView.OnQueryTextListener{

    private String[] drawerListViewItems;
    private ListView drawerListView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private CharSequence titleBar;
    private CharSequence titleMenu;
    private Intent intent;
    private TextView mStatusView;
    private SearchView mSearchView;
    private TableLayout table_UE_preferences;
    private TableLayout table_UE_recherche;
    private TableRow row_results;
    private TextView item_UE_preferences;
    private Animation anim_clic_on_item;
    private AlertDialog.Builder alertDeleteUEBuilder;
    private AlertDialog.Builder alertAddUEBuilder;
    private AlertDialog alertDeleteUE;
    private TextView item_UE_recherche;
    private AlertDialog alertAddUE;

    protected void onCreate(Bundle savedInstanceState){
        //Définition de la searchBar
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_ue);

        //Définition des éléments de la page
        table_UE_preferences = (TableLayout)findViewById(R.id.table_UE_preferences);
        table_UE_recherche = (TableLayout)findViewById(R.id.table_UE_results);
//        mStatusView = (TextView)findViewById(R.id.text_status_searchBar);

        //Définition des animations
        anim_clic_on_item = AnimationUtils.loadAnimation(this, R.anim.fadein);

        //Définition des titres
        titleBar = getResources().getString(R.string.label_listeUE);
        titleMenu = getResources().getString(R.string.label_menu);

        //Initialisation de la liste des préférences
        setTable_UE_preferences();

        //Définition de la boîte de dialogue de confirmation de modification de la note
        alertDeleteUEBuilder = new AlertDialog.Builder(this);
        alertAddUEBuilder = new AlertDialog.Builder(this);

        // Récupérer les items du menu et la vue du menu
        drawerListViewItems = getResources().getStringArray(R.array.menu_items_profil);
        drawerListView = (ListView) findViewById(R.id.left_drawer_listeUE);

        // Initialisation de l'adapter
        drawerListView.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_listview_item, drawerListViewItems));

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout_listeUE);

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

    //TODO
    /*Code à redéfinir pour récupérer les résultats de la recherche*/
    //Affichage des résultats de la recherche d'UE
    private void setTable_recherche_UE() {
        final String [] col_items = {"L3-Info Projet d'étude de cas tutoré (CM)", "L3-Info Projet d'étude de cas tutoré (TP)", "L3-Info Programmation Orientation Objet (TD)", "L3-Info Programmation Orientation Objet (TP)"};

        // Construction du tableau
        for(int i=0;i<col_items.length;i++) {
            final int position = i;
            row_results = new TableRow(this);
            item_UE_recherche = new TextView(this);
            item_UE_recherche.setText(col_items[i]);
            setStyle(item_UE_recherche);
            item_UE_recherche.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            // ajout des cellules à la ligne
            row_results.addView(item_UE_recherche);

            //Gestion du clic de l'utilisateur sur une UE
            row_results.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    table_UE_recherche.getChildAt(position).startAnimation(anim_clic_on_item);
                    initalertAddUEBuilder(position);
                    alertAddUE = alertAddUEBuilder.create();
                    alertAddUE.show();
                }
            });

            // ajout de la ligne au tableau
            table_UE_recherche.addView(row_results);
        }
    }

    //Affichage des résultats
    private void setTable_UE_preferences() {
        final String [] col_items = {"L1-Info Algorithmique (CM)", "M1-Info Processus stochastiques et heuristiques (CM)", "M1-Info Modélisation et optimisation des systèmes (CM)", "L1-Info Algorithmique (TD)", "M1-Info Processus stochastiques et heuristiques (TD)", "M1-Info Architecture systèmes (CM)"};

        // Construction du tableau
        for(int i=0;i<col_items.length;i++) {
            final int position = i;
            row_results = new TableRow(this);
            item_UE_preferences = new TextView(this);
            item_UE_preferences.setText(col_items[i]);
            setStyle(item_UE_preferences);
            item_UE_preferences.setLayoutParams( new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            // ajout des cellules à la ligne
            row_results.addView(item_UE_preferences);

            //Gestion du clic de l'utilisateur sur une UE
            row_results.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    table_UE_preferences.getChildAt(position).startAnimation(anim_clic_on_item);
                    initalertDeleteUEBuilder(position);
                    alertDeleteUE = alertDeleteUEBuilder.create();
                    alertDeleteUE.show();
                }
            });

            // ajout de la ligne au tableau
            table_UE_preferences.addView(row_results);
        }
    }

    // Définition du style des cellules du tableau
    private void setStyle(TextView cellule) {
        cellule.setGravity(Gravity.CENTER);
        cellule.setTextColor(getResources().getColor(R.color.white));
        cellule.setPadding(4, 4, 4, 4);
        cellule.setTextSize(18);
    }

    //Définition du contenu de la boîte de dialogue DeleteUE
    private void initalertDeleteUEBuilder(int i) {
        final int position = i;
        alertDeleteUEBuilder.setTitle(R.string.alertSuppressionUETitle);
        String confirm_txt = getResources().getString(R.string.alertSuppressionUEMessage);
        final TableRow temp_row = (TableRow)table_UE_preferences.getChildAt(position);
        final TextView temp_UE = (TextView) temp_row.getChildAt(0);
        String UE_name = (String) temp_UE.getText();
        alertDeleteUEBuilder.setMessage(confirm_txt+" "+UE_name+" ?");
        alertDeleteUEBuilder.setIcon(R.drawable.ic_action_remove);

        alertDeleteUEBuilder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /* TODO */
                /*Code à définir pour supprimer l'UE de la liste de préférences dans la bdd et à prendre en compte dans l'affichage*/
                temp_row.removeView(temp_UE);
            }
        });
        alertDeleteUEBuilder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

    //Définition du contenu de la boîte de dialogue AddUE
    private void initalertAddUEBuilder(int i) {
        final int position = i;
        alertAddUEBuilder.setTitle(R.string.alertAjoutUETitle);
        String confirm_txt = getResources().getString(R.string.alertAjoutUEMessage);
        final TableRow temp_row_liste_pref = new TableRow(this);
        final TableRow temp_row = (TableRow)table_UE_recherche.getChildAt(position);
        final TextView temp_UE = (TextView) temp_row.getChildAt(0);
        String UE_name = (String) temp_UE.getText();
        alertAddUEBuilder.setMessage(confirm_txt+" "+UE_name+" ?");
        alertAddUEBuilder.setIcon(R.drawable.ic_action_new);

        alertAddUEBuilder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /* TODO */
                /*Code à définir pour supprimer l'UE de la liste de préférences dans la bdd et à prendre en compte dans l'affichage*/
                temp_row.removeView(temp_UE);
                temp_row_liste_pref.addView(temp_UE);
                table_UE_preferences.addView(temp_row_liste_pref);
            }
        });
        alertAddUEBuilder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
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

    // ******************************
    //Gestion de la barre de recherche
    // ******************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_searchview, menu);

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

//                Log.i("Searchables List", searchables.get(0).getSuggestIntentData());
//                Log.i("Searchables List", searchables.get(1).getSuggestIntentData());
//
//                Log.i("Searchables List", searchables.get(0).getSuggestAuthority());
//                Log.i("Searchables List", searchables.get(1).getSuggestAuthority());

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
        table_UE_recherche.removeAllViews();
        setTable_recherche_UE();
        mSearchView.clearFocus();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
//        mStatusView.setText("Query = " + newText);
        return false;
    }

    // Implémentation de la gestion du clic sur un item
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            /* Ce qu'il se passe quand on clique sur un item du menu */
            switch (position){
                case 0:
                    intent = new Intent(ListeUE.this, Accueil.class);
                    startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(ListeUE.this, PlanningProf.class);
                    startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(ListeUE.this, PlanningSalle.class);
                    startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(ListeUE.this, Reservation.class);
                    startActivity(intent);
                    break;
                case 4:
                    intent = new Intent(ListeUE.this, Notes.class);
                    startActivity(intent);
                    break;
                case 5:
                    intent = new Intent(ListeUE.this, Connexion.class);
                    startActivity(intent);
                    /* TODO */
                    /* Définir la déconnexion de l'utilisateur à l'application */
                    Toast.makeText(getApplicationContext(), R.string.boutonCoDeconnexionToast, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
