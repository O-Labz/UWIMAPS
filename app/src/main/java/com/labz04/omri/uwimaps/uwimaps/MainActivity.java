package com.labz04.omri.uwimaps.uwimaps;


import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.labz04.omri.uwimaps.R;
import com.labz04.omri.uwimaps.database.LocationDatabase;
import com.labz04.omri.uwimaps.fragments.BankFragment;
import com.labz04.omri.uwimaps.fragments.BuildingsFragment;
import com.labz04.omri.uwimaps.fragments.Frag;
import com.labz04.omri.uwimaps.fragments.GazFragment;
import com.labz04.omri.uwimaps.fragments.HallFragment;
import com.labz04.omri.uwimaps.fragments.HumFragment;
import com.labz04.omri.uwimaps.fragments.LawFragment;
import com.labz04.omri.uwimaps.fragments.LectureTheatresFragment;
import com.labz04.omri.uwimaps.fragments.LibrariesFragment;
import com.labz04.omri.uwimaps.fragments.ListFrag;
import com.labz04.omri.uwimaps.fragments.MedFragment;
import com.labz04.omri.uwimaps.fragments.RestFragment;
import com.labz04.omri.uwimaps.fragments.ScienceFragment;
import com.labz04.omri.uwimaps.fragments.SocialFragment;
import com.labz04.omri.uwimaps.fragments.StallFragment;
import com.labz04.omri.uwimaps.gps.GPS_MODULE;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{


    GPS_MODULE gps;
    MapFragment north;
    GoogleMap map;
    LocationDatabase myDB;
    Toolbar toolbar;
    MapChoice choice;
    private final static String TAG = MainActivity.class.getName().toString();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mAdView.setAdListener(new AdListener() {
            public void onAdLoaded() {
            }

            public void onAdFailedToLoad(int errorcode) {
            }

            public void onAdOpened() {
            }

            public void onAdClosed() {
            }

            public void onAdLeftApplication() {
            }
            // Only implement methods you need.
        });


        myDB = new LocationDatabase(this);
        SQLiteDatabase adb = myDB.getWritableDatabase();

        String count = "SELECT count(*) FROM location_table";
        Cursor cursor = adb.rawQuery(count, null);
        cursor.moveToFirst();
        int icount = cursor.getInt(0);
        if (icount > 0) {

        } else
            AddMyData();


        if (savedInstanceState == null)
        {
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.content_only, new Frag()).commit();


            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            //myDB.deleteAll_Data();
            //ViewAll_Data(); // Shows all the data in location database
        }

//        //This section takes info from search and processes it
//        Intent search_intent = getIntent();
//        Bundle search_bundle = search_intent.getExtras();
//
//        if(search_bundle != null)
//        {
//            setContentView(R.layout.fragment_search);
//            String search_string =(String) search_bundle.get("fragment_link");
////            Textv = (TextView)findViewById(R.id.textViewSearch);
////            Textv.setText(search_string);
//            Toast.makeText(getApplicationContext(), search_string, Toast.LENGTH_SHORT).show();
//        }
        //This section above takes info from search and processes it


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }



//    @Override
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//
////        getMenuInflater().inflate(R.menu.main, menu);
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main, menu);
//
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
////            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
////            final SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
////            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
//            MenuItem searchItem = menu.findItem(R.id.search);
//            final SearchView search = (SearchView) MenuItemCompat.getActionView(searchItem);
//            search.onActionViewExpanded();
//            search.requestFocus();
//            search.setOnQueryTextListener(new SearchView.OnQueryTextListener()
//            {
//
//                @Override
//                public boolean onQueryTextSubmit(String s) {
//                    //Log.d(TAG, "onQueryTextSubmit ");
//                    cursor=studentRepo.getStudentListByKeyword(s);
//                    if (cursor==null){
//                        Toast.makeText(MainActivity.this,"No records found!",Toast.LENGTH_LONG).show();
//                    }else{
//                        Toast.makeText(MainActivity.this, cursor.getCount() + " records found!",Toast.LENGTH_LONG).show();
//                    }
//                    customAdapter.notifyDataSetChanged();
//                    customAdapter.swapCursor(cursor);
//                    search.clearFocus();
//                    return true;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String s) {
//                    Log.d(TAG, "onQueryTextChange ");
//
//                    cursor=studentRepo.getStudentListByKeyword(s);
//                    if (cursor!=null)
//                    {
//                        customAdapter.notifyDataSetChanged();
//                        customAdapter.swapCursor(cursor);
//                    }
//                    return false;
//                }
//
//            });
//
//        }
//
//        return super.onCreateOptionsMenu(menu);
//
//
//    }


    public void SelectMapType()
    {
        MapChoice dialog = new MapChoice();
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            startActivity(new Intent(this, About.class));
            return true;
        }
        else if (id == R.id.action_instructions)
        {
            startActivity(new Intent(this,Help.class));
            return true;
        }
        else if (id == R.id.search1)
        {
//            startActivity(new Intent(this, SearchActivity.class));
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
//            fm.beginTransaction().replace(R.id.content_only, new ListFrag(), TAG).commit();
            toolbar.setTitle("Quick Search");
            fm.beginTransaction().replace(R.id.content_only, new ListFrag()).commit();
            return true;
        }
        else if (id == R.id.action_type)
        {
            SelectMapType();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //FragmentManager fm1 = getFragmentManager();
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();


        int id = item.getItemId();

        if (id == R.id.nav_Home) {
            // Handle the camera action
            toolbar.setTitle("U.W.I MAPS");
            fm.beginTransaction().replace(R.id.content_only, new Frag()).commit();

        } else if (id == R.id.nav_science) {
            toolbar.setTitle("Science and Tech");
            fm.beginTransaction().replace(R.id.content_only, new ScienceFragment()).commit();

        } else if (id == R.id.nav_social) {
            toolbar.setTitle("Social Sciences");
            fm.beginTransaction().replace(R.id.content_only, new SocialFragment()).commit();

        } else if (id == R.id.nav_med) {
            toolbar.setTitle("Medical Science");
            fm.beginTransaction().replace(R.id.content_only, new MedFragment()).commit();

        } else if (id == R.id.nav_hum) {
            toolbar.setTitle("Humanities and Edu");
            fm.beginTransaction().replace(R.id.content_only, new HumFragment()).commit();

        } else if (id == R.id.nav_law) {
            toolbar.setTitle("Law");
            fm.beginTransaction().replace(R.id.content_only, new LawFragment()).commit();

        } else if (id == R.id.nav_gaz) {
            toolbar.setTitle("Gazebos");
            fm.beginTransaction().replace(R.id.content_only, new GazFragment()).commit();

        } else if (id == R.id.nav_stall) {
            toolbar.setTitle("Stalls");
            fm.beginTransaction().replace(R.id.content_only, new StallFragment()).commit();

        } else if (id == R.id.nav_gallery) {
            toolbar.setTitle("Restaurants");
            fm.beginTransaction().replace(R.id.content_only, new RestFragment()).commit();

        } else if (id == R.id.nav_slideshow) {
            toolbar.setTitle("Halls of Residence");
            fm.beginTransaction().replace(R.id.content_only, new HallFragment()).commit();

        } else if (id == R.id.nav_buildings) {
            toolbar.setTitle("Important Buildings");
            fm.beginTransaction().replace(R.id.content_only, new BuildingsFragment()).commit();

        } else if (id == R.id.nav_Theaters) {
            toolbar.setTitle("Major Lecture Theaters");
            fm.beginTransaction().replace(R.id.content_only, new LectureTheatresFragment()).commit();

        } else if (id == R.id.nav_Libraries) {
            toolbar.setTitle("Libraries");
            fm.beginTransaction().replace(R.id.content_only, new LibrariesFragment()).commit();

        } else if (id == R.id.nav_Banks) {
            toolbar.setTitle("Campus Banks");
            fm.beginTransaction().replace(R.id.content_only, new BankFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Show All Data.....this function is used to see the contents of the students database
    public void ViewAll_Data() {
        Cursor rez = myDB.getAll_Data();
        if (rez.getCount() == 0) {
            Showmessage("Error", "Nothing Found");
            return;
        }

        StringBuffer buffer = new StringBuffer();

        while (rez.moveToNext()) {
            buffer.append("Id :" + rez.getString(0) + "\n");
            buffer.append("Node :" + rez.getString(1) + "\n");
            buffer.append("Latitude :" + rez.getDouble(2) + "\n");
            buffer.append("Longitude :" + rez.getDouble(3) + "\n");
        }

        Showmessage("Database Content", buffer.toString());
    }


    //Show All Data.....this function is used to see the contents of the database
    public void Showmessage(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }


    //This Function Adds all the data to the Database Created
    public void AddMyData()
    {
        Toast.makeText(MainActivity.this, "Database Install Successful", Toast.LENGTH_LONG).show();
        if (myDB != null) {
            myDB.putInData("Student Administration Services",18.005979	,-76.747481);
            myDB.putInData("Mona Information Technology Services" ,18.003268,-76.745261);
            myDB.putInData("Placement & Careers" ,18.004786,	-76.746695);
            myDB.putInData("U.W.I Chapel" ,18.003614, -76.748096);
            myDB.putInData("Book Shop" ,18.004780, -76.747066);
            myDB.putInData("National Commercial Bank" ,18.004881, -76.747011);
            myDB.putInData("Bank of Nova Scotia" ,18.005509, -76.744914);
            myDB.putInData("Jamaica National" ,18.0061076,-76.7448295);
            myDB.putInData("U.W.I Credit Union" ,18.0015973,-76.7457329);
            myDB.putInData("Students Union" ,18.000734,-76.743382);
            myDB.putInData("Bursary" ,18.005979	,-76.747481);
            myDB.putInData("Lodgings " ,18.002791,-76.746298);
            myDB.putInData("News Talk" ,18.004786,-76.746695);
            myDB.putInData("Phillip Sherlock" ,18.003536, -76.746307);
            myDB.putInData("CARIMAC" ,18.003227, -76.747022);
            myDB.putInData("Office of Student Services and Development" ,18.002842, -76.746820);
            myDB.putInData("Commuting Students Lounge",18.0051758,-76.745592);
            myDB.putInData("Assembly Hall",18.005395,-76.747325);

            myDB.putInData("Chemistry Main Office" ,18.0044993,-76.7491293);
            myDB.putInData("Chemistry Lecture Theater 1" ,18.004303, -76.749864);
            myDB.putInData("Chemistry Lecture Theater 2" ,18.004303, -76.749864);
            myDB.putInData("Chemistry Lecture Theater 3" ,18.004453, -76.749619);
            myDB.putInData("Chemistry Lecture Theater 4" ,18.004529, -76.750232);
            myDB.putInData("Chemistry Lecture Theater 5" ,18.004282, -76.750224);
            myDB.putInData("Chemistry Lecture Theater 6" ,18.004577, -76.750051);
            myDB.putInData("Chemistry Lecture Theater 7" ,18.004645, -76.749950);
            myDB.putInData("Chemistry Physics Lecture Theater" 	,18.004392, -76.749100);

            myDB.putInData("Computing Main Office" ,18.006016, -76.749597);
            myDB.putInData("Computing Lecture Room" ,18.005901,-76.749803);
            myDB.putInData("CR1" ,18.005157	,-76.750003);
            myDB.putInData("CR2" ,18.0051564,-76.750038);
            myDB.putInData("Lab1" ,18.0052025,-76.7499753);
            myDB.putInData("Lab2" ,18.005901,-76.749803);
            myDB.putInData("Software Engineering Lab" ,18.0064435,-76.7485883);

            myDB.putInData("Room 1" ,18.006131,-76.749020);
            myDB.putInData("Room 2" ,18.006131,-76.749020);
            myDB.putInData("Room 3" ,18.006131,-76.749020);
            myDB.putInData("Room 4" ,18.006131,-76.749020);
            myDB.putInData("Room 5" ,18.006131,-76.749020);
            myDB.putInData("Room 6" ,18.006131,-76.749020);
            myDB.putInData("Museum" ,18.006131,-76.749020);
            myDB.putInData("Marine Geology Unit" ,18.006016, -76.749323);
            myDB.putInData("Lab 1" ,18.006131,-76.749020);
            myDB.putInData("Lab 2" ,18.006131,-76.749020);
            myDB.putInData("Lab 3" ,18.006131,-76.749020);

            myDB.putInData("Life Science Main Office" ,18.006182,-76.750341);
            myDB.putInData("Life Science Lecture Theater 1" ,18.006182,-76.750342);
            myDB.putInData("Life Science Lecture Theater 2" ,18.006182,-76.750343);
            myDB.putInData("Life Science Lecture Theater 3" ,18.006182,-76.750340);
            myDB.putInData("Life Science Lecture Theater 4" ,18.006182,-76.750340);
            myDB.putInData("Life Science Lecture Theater 5" ,18.006182,-76.750340);
            myDB.putInData("Aquatic Sciences Lab" ,18.006182,-76.750340);
            myDB.putInData("Block A" ,18.006182,-76.750348);
            myDB.putInData("Block B" ,18.006182,-76.750346);
            myDB.putInData("Block C" ,18.006182,-76.750344);
            myDB.putInData("Block D" ,18.006182,-76.750345);
            myDB.putInData("Block E" ,18.006182,-76.750345);

            myDB.putInData("Physics Main Office" ,18.004836	,-76.748823);
            myDB.putInData("Phys A" ,18.004928,-76.749047);
            myDB.putInData("Phys B" ,18.0049536, -76.7490518);
            myDB.putInData("Phys C" ,18.0049459,-76.7490336);
            myDB.putInData("Phys D" ,18.004500,-76.749037);
            myDB.putInData("Electronics Lab" ,18.004620,-76.748906);
            myDB.putInData("Virtual Lab" ,18.004586,-76.749019);
            myDB.putInData("Earthquake Unit" ,18.004586,-76.749019);
            myDB.putInData("Electronics Unit" ,18.004586,-76.749019);

            myDB.putInData("Engineering Main Office" ,18.004615, -76.750417);
            myDB.putInData("Engineering Lecture Theater 1" ,18.0051555, -76.7497084);
            myDB.putInData("Engineering Lecture Theater 2" ,18.0051632,-76.7497196);
            myDB.putInData("Engineering Lecture Theater 3" ,18.0051548,-76.749703);
            myDB.putInData("Engineering Lecture Theater 4" ,18.0050984,-76.7496972);
            myDB.putInData("Engineering Lecture Theater 5" ,18.0051141,-76.7496359);
            myDB.putInData("Electronics Engineering Lab" ,18.0051221,-76.7495705);

            myDB.putInData("Humanities Gazebos"	,18.0048848	,-76.7462619);
            myDB.putInData("Nursing Gazebos" ,18.003946,-76.744698);
            myDB.putInData("Science and Tech Gazebos" ,18.005288,-76.748512);
            myDB.putInData("Law Gazebos" ,18.008171,-76.748520);
            myDB.putInData("Mona School of Business Gazebos" ,18.008303,-76.748074);
            myDB.putInData("Commuting Lounge Gazebos",18.0051758, -76.745592);

            myDB.putInData("Chancellor Hall Office" ,18.005720,-76.744179);
            myDB.putInData("Block Aye" ,18.005982, -76.744221);
            myDB.putInData("Block Runci" ,18.006530, -76.744502);
            myDB.putInData("Block Che'" ,18.006026,-76.743852);
            myDB.putInData("Block Dynamite" ,18.006647, -76.743992);
            myDB.putInData("Block X" ,18.005619, -76.744567);

            myDB.putInData("ABC Hall Office",18.012959,-76.743555);
            myDB.putInData("ABC Block A",18.013131,-76.743253);
            myDB.putInData("ABC Block B",18.013131,-76.743253);
            myDB.putInData("ABC Block C",18.013131,-76.743253);

            myDB.putInData("Taylor Hall Office",18.007293,-76.744232);
            myDB.putInData("Block Attica",18.008012,-76.744153);
            myDB.putInData("Block Butchers",18.007970,-76.744796);
            myDB.putInData("Block Roosters",18.007495,-76.744007);
            myDB.putInData("Block Stallion",18.007360,-76.744486);
            myDB.putInData("Block G",18.008415,-76.744475);

            myDB.putInData("Tower Hall Office",18.007665,-76.742904);
            myDB.putInData("Tower 1",18.008046,-76.743466);
            myDB.putInData("Tower 2",18.007802,-76.743489);
            myDB.putInData("Tower 3",18.007665,-76.742904);
            myDB.putInData("Tower 4",18.007243,-76.743589);
            myDB.putInData("Tower 5",18.006986,-76.743609);

            myDB.putInData("Mary Seacole Hall Office",18.004750,-76.745292);
            myDB.putInData("Jade Block",18.004024,-76.745460);
            myDB.putInData("Great Block",18.004055,-76.745089);
            myDB.putInData("Hype Block",18.004349,-76.745482);
            myDB.putInData("Crowd Block",18.004544,-76.745215);
            myDB.putInData("Seacole Block Dynamite",18.004558,-76.745024);

            myDB.putInData("Irvin Hall Office",18.006083,-76.742349);
            myDB.putInData("Block Alpha",18.006198,-76.742466);
            myDB.putInData("Block Bears",18.006432,-76.742841);
            myDB.putInData("Block Chalise",18.006746,-76.742501);
            myDB.putInData("Freshers Block",18.007306,-76.742814);

            myDB.putInData("Rex Hall Office",18.002636,-76.741620);
            myDB.putInData("Cluster 1",18.001685,-76.741456);
            myDB.putInData("Cluster 2",18.001798,-76.741715);
            myDB.putInData("Cluster 3",18.002032,-76.742028);
            myDB.putInData("Cluster 4",18.002473,-76.742419);
            myDB.putInData("Cluster 5",18.002892,-76.742475);
            myDB.putInData("Cluster 6",18.003125,-76.742189);
            myDB.putInData("Cluster 7",18.003683,-76.742070);
            myDB.putInData("Cluster 8",18.003161,-76.741626);
            myDB.putInData("Cluster 9",18.003629,-76.741418);

            myDB.putInData("Preston Hall Office",18.000490,-76.742613);
            myDB.putInData("Cluster Amsterdam",18.001388,-76.742028);
            myDB.putInData("Cluster Belqique",18.001317,-76.742538);
            myDB.putInData("Cluster Burgplatz",18.001424,-76.742843);
            myDB.putInData("Cluster Einheit",17.999858,-76.741872);
            myDB.putInData("Cluster Italia",18.000169,-76.741829);
            myDB.putInData("Cluster Los Matadores",18.000516,-76.742017);
            myDB.putInData("Cluster Sherwood Manor",17.999944,-76.742940);
            myDB.putInData("Cluster Regensen",17.999893,-76.742189);
            myDB.putInData("Cluster Olympia",18.000970,-76.742350);
            myDB.putInData("Cluster La Maison",18.001742,-76.742551);

            myDB.putInData("138 Office",18.002074,-76.744160);
            myDB.putInData("T 1",18.002247,-76.744059);
            myDB.putInData("T 2",18.002166,-76.743909);
            myDB.putInData("T 3",18.002166,-76.743909);

            myDB.putInData("New Post Grad",18.000566,-76.745873);
            myDB.putInData("Old PostGrad",18.001680,-76.743406);

            myDB.putInData("Humanities Faculty Main Office" ,18.0052098,-76.7461714);
            myDB.putInData("N 1" ,18.0051723,-76.746152);
            myDB.putInData("N 2" ,18.0051884,-76.746152);
            myDB.putInData("N 3" ,18.0051908,-76.7461325);
            myDB.putInData("N 4" ,18.0052132,-76.7461167);
            myDB.putInData("NELT" ,18.0057788,-76.7466837);
            myDB.putInData("Caribbean Studies" ,18.0052622, -76.7461434);
            myDB.putInData("English Lit" ,18.0052255,-76.7461625);
            myDB.putInData("School of Education" ,18.005354,-76.745676);
            myDB.putInData("Modern Language and literature" ,18.004788,-76.7467708);
            myDB.putInData("Library and Information Studies" ,18.0047863	,-76.746868);
            myDB.putInData("School of Education Lecture Theater" ,18.005354,-76.745676);

            myDB.putInData("Law Faculty Main Office" ,18.008244, -76.748480);
            myDB.putInData("Lecture Room 1A" ,18.008244, -76.748480);
            myDB.putInData("Lecture Room 2B" ,18.008244, -76.748480);
            myDB.putInData("Lecture Room 1" ,18.008244, -76.748480);
            myDB.putInData("Lecture Room 2" ,18.008171,-76.748520);
            myDB.putInData("Lecture Room 3" ,18.008244, -76.748480);
            myDB.putInData("Seminar Room 1" ,18.008244, -76.748480);
            myDB.putInData("Seminar Room 2" ,18.008171,-76.748520);

            myDB.putInData("Science Lecture Theater" ,18.005187, -76.749936);
            myDB.putInData("Pre Clinical Theater1" ,18.005139, -76.749792);
            myDB.putInData("Inter-faculty Lecture Theater" ,18.005653, -76.748783);
            myDB.putInData("Biology Lecture Theater" ,18.006084,-76.750487);

            myDB.putInData("Social Science Lecture Theater" ,18.006695, -76.747406);
            myDB.putInData("GLT 1" ,18.007913,-76.747629);
            myDB.putInData("GLT 2" ,18.007913,-76.747629);
            myDB.putInData("GLT 3" ,18.007913,-76.747629);
            myDB.putInData("SR  1 - 23" ,18.0071234,-76.7471699);
            myDB.putInData("Psychology Unit" ,18.007913,-76.747629);

            myDB.putInData("New Arts Block" ,18.0049568,-76.7464424);
            myDB.putInData("Life Science Main Office" ,18.006182,-76.750340);
            myDB.putInData("Life Science Lecture Theater1" ,18.006182,-76.750340);
            myDB.putInData("Life Science Lecture Theater2" ,18.006182,-76.750340);
            myDB.putInData("Life Science Lecture Theater3" ,18.006182,-76.750340);
            myDB.putInData("Life Science Lecture Theater4" ,18.006182,-76.750340);
            myDB.putInData("Life Science Lecture Theater5",18.006182,-76.750340);

            myDB.putInData("Math Main Office" ,18.004714, -76.749561);
            myDB.putInData("Math Lecture theater 1" ,18.004714, -76.749561);
            myDB.putInData("Math Lecture theater 2" ,18.004714, -76.749561);
            myDB.putInData("Math Lecture theater 3", 18.004748, -76.749460);
            myDB.putInData("Math Lab", 18.004748, -76.749460);
            myDB.putInData("Postgrad Room 1", 18.004748, -76.749460);
            myDB.putInData("Postgrad Room 2", 18.004748, -76.749460);
            myDB.putInData("Postgrad Room 3", 18.004748, -76.749460);

            myDB.putInData("Main Library" ,18.006332,-76.745874);
            myDB.putInData("Science Library" ,18.005309, -76.749467);
            myDB.putInData("Social Science Library" ,18.006381,-76.748001);
            myDB.putInData("Med Library" ,18.010876,-76.745790);
            myDB.putInData("Law Library" ,18.008171	,-76.748520);
            myDB.putInData("Old Library" ,18.000991, -76.744840);

            myDB.putInData("Med Faculty Main Office" ,18.009653,-76.746733);
            myDB.putInData("Child and Adolescents" ,18.009653,-76.746733);
            myDB.putInData("Community Health Psychiatry" ,18.009653,-76.746733);
            myDB.putInData("Medicine" ,18.009653,-76.746733);
            myDB.putInData("Micro Biology" ,18.009653,-76.746733);
            myDB.putInData("Surgery" ,18.009653,-76.746733);
            myDB.putInData("School of Nursing" ,18.003890, -76.744349);
            myDB.putInData("Radiology" ,18.009653,-76.746733);
            myDB.putInData("Anaesthesia" ,18.009653,-76.746733);
            myDB.putInData("Intensive Care" ,18.009653,-76.746733);
            myDB.putInData("Obstetrics Gynaecology" ,18.009653,-76.746733);

            myDB.putInData("Kentucky Fried Chicken" ,18.006140,-76.744805);
            myDB.putInData("The Spot" ,18.000793,-76.743276);
            myDB.putInData("Yao" ,18.000793,-76.743276);
            myDB.putInData("Juici Patties" ,18.005114,-76.748565);
            myDB.putInData("Pages Cafe'" ,18.006234,-76.744835);
            myDB.putInData("BeeHive" ,18.004488, -76.746364);
            myDB.putInData("Dukunoo Deli" ,18.007227, -76.747139);
            myDB.putInData("Bucks" ,18.007630,-76.748592);

            myDB.putInData("Faculty Office" ,18.0071244,-76.7471703);
            myDB.putInData("Hotel and Tourism" 	,18.007125,-76.7471669);
            myDB.putInData("Economics" ,18.006531,-76.746878);
            myDB.putInData("Government" ,18.006531,-76.746878);
            myDB.putInData("Mona School of Business North" ,18.007952, -76.747375);
            myDB.putInData("Mona School of Business South" ,18.006381,-76.748001);
            myDB.putInData("Sociology and Social Work" 	,18.0071231,-76.7471676);
            myDB.putInData("Psychology Unit" ,18.0071241,-76.7471698);
            myDB.putInData("Institute for Gender and Development" 	,18.0071241,-76.7471698);

            myDB.putInData("Main Library Stall" ,18.005484,	-76.745527);
            myDB.putInData("Humanities Stall" 	,18.0050561,-76.7459129);
            myDB.putInData("Social Science Stall" ,18.0065191,-76.7469276);
            myDB.putInData("Science and Tech Stall 1" ,18.0049263,-76.74912);
            myDB.putInData("Science and Tech Stall 2" ,18.0049442,-76.74912);

            myDB.putInData("UWI Health Center",18.004315,-76.743712);
            myDB.putInData("University Hospital",18.011353,-76.744420);
            Toast.makeText(MainActivity.this, "Location Database Updated", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(MainActivity.this,"All is Not Well Database not installed ", Toast.LENGTH_SHORT).show();
    }
}
