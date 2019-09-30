package com.labz04.omri.uwimaps.uwimaps;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.labz04.omri.uwimaps.R;
import com.labz04.omri.uwimaps.adapter.CustomAdapter;
import com.labz04.omri.uwimaps.fragments.Frag;
import com.labz04.omri.uwimaps.fragments.LectureTheatresFragment;
import com.labz04.omri.uwimaps.fragments.ScienceFragment;


/**
 * Created by omri on 3/22/16.
 */
public class SearchActivity extends AppCompatActivity

{
    Toolbar ytoolbar;
    private String text;
    private CustomAdapter customAdapter;
    ListView listView;
    Cursor cursor;
    StudentRepo studentRepo ;
    private final static String TAG= MainActivity.class.getName().toString();
    private static int Screen_Delay = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        ytoolbar = (Toolbar) findViewById(R.id.y_ttoolbar);
        ytoolbar.setTitleTextColor(android.graphics.Color.WHITE);
        setSupportActionBar(ytoolbar);

        studentRepo = new StudentRepo(this);
        cursor=studentRepo.getStudentList();
        customAdapter = new CustomAdapter(SearchActivity.this,  cursor, 0);
        listView = (ListView) findViewById(R.id.lstStudent);
        listView.setAdapter(customAdapter);

        //Get Item when text view clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,int position, long arg3) {
                        String Item_name = listView.getItemAtPosition(position).toString();


                        TextView textView = (TextView) view.findViewById(R.id.txtName);
                        text = textView.getText().toString();
                       // Toast.makeText(getApplicationContext(),  text , Toast.LENGTH_SHORT).show();


                        Intent intent = null;
                        intent  = new Intent(SearchActivity.this, MainActivity.class);
                        //This part of the function sends  "fragment_link text" to tha main activity to be processed
                        intent.putExtra("fragment_link",text);


                        if(intent != null)
                        {
                            startActivity(intent);
                        }


//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                Intent i = new Intent(SearchActivity.this, MainActivity.class);
//                                startActivity(i);
//                                ((Activity) SearchActivity.this).overridePendingTransition(0,0);
//
//                        }
//                       }, Screen_Delay);

                    }
                }
        );

        // Get a support ActionBar corresponding to this toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        if(cursor==null)
        {
            insertDummy();
        }



    }

    @Override
    public void onResume(){
        super.onResume();

    }


    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu, menu);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String s) {
                    Log.d(TAG, "onQueryTextSubmit ");
                    cursor=studentRepo.getStudentListByKeyword(s);
                    if (cursor==null){
                        Toast.makeText(SearchActivity.this,"No records found!",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(SearchActivity.this, cursor.getCount() + " records found!",Toast.LENGTH_LONG).show();
                    }
                    customAdapter.swapCursor(cursor);

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    Log.d(TAG, "onQueryTextChange ");
                    cursor=studentRepo.getStudentListByKeyword(s);
                    if (cursor!=null){
                        customAdapter.swapCursor(cursor);
                    }
                    return false;
                }

            });

        }

        return true;

    }

    private void insertDummy(){
        Student student= new Student();

        studentRepo = new StudentRepo(this); student.name="Student Administration Services";student.latitude = 18.005979	; student.longitude = -76.747481; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Mona Information Technology Services" ;student.latitude = 18.003268; student.longitude = -76.745261; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Placement & Careers" ;student.latitude = 18.004786; student.longitude = -76.746695; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="U.W.I Chapel" ;student.latitude = 18.003614; student.longitude = -76.748096; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Book Shop" ;student.latitude = 18.004780; student.longitude = -76.747066; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="National Commercial Bank" ;student.latitude = 18.004881; student.longitude = -76.747011; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Bank of Nova Scotia" ;student.latitude = 18.005509; student.longitude = -76.744914; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Jamaica National" ;student.latitude = 18.0061076; student.longitude = -76.7448295; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="U.W.I Credit Union" ;student.latitude = 18.0015973; student.longitude = -76.7457329; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Students Union" ;student.latitude = 18.000734; student.longitude = -76.743382; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Bursary" ;student.latitude = 18.005979	; student.longitude = -76.747481; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Lodgings " ;student.latitude = 18.002791; student.longitude = -76.746298; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="News Talk" ;student.latitude = 18.004786; student.longitude = -76.746695; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Phillip Sherlock" ;student.latitude = 18.003536; student.longitude = -76.746307; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="CARIMAC" ;student.latitude = 18.003227; student.longitude = -76.747022; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Office of Student Services and Development" ;student.latitude = 18.002842; student.longitude = -76.746820; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Commuting Students Lounge";student.latitude = 18.0051758; student.longitude = -76.745592; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Assembly Hall";student.latitude = 18.005395; student.longitude = -76.747325; studentRepo.insert(student);

        studentRepo = new StudentRepo(this); student.name="Chemistry Main Office" ;student.latitude = 18.0044993; student.longitude = -76.7491293; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Chemistry Lecture Theater 1" ;student.latitude = 18.004303; student.longitude = -76.749864; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Chemistry Lecture Theater 2" ;student.latitude = 18.004303; student.longitude = -76.749864; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Chemistry Lecture Theater 3" ;student.latitude = 18.004453; student.longitude = -76.749619; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Chemistry Lecture Theater 4" ;student.latitude = 18.004529; student.longitude = -76.750232; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Chemistry Lecture Theater 5" ;student.latitude = 18.004282; student.longitude = -76.750224; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Chemistry Lecture Theater 6" ;student.latitude = 18.004577; student.longitude = -76.750051; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Chemistry Lecture Theater 7" ;student.latitude = 18.004645; student.longitude = -76.749950; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Chemistry Physics Lecture Theater" 	;student.latitude = 18.004392; student.longitude = -76.749100; studentRepo.insert(student);

        studentRepo = new StudentRepo(this); student.name="Computing Main Office" ;student.latitude = 18.006016; student.longitude = -76.749597; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Computing Lecture Room" ;student.latitude = 18.005901; student.longitude = -76.749803; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="CR1" ;student.latitude = 18.005157	; student.longitude = -76.750003; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="CR2" ;student.latitude = 18.0051564; student.longitude = -76.750038; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Lab1" ;student.latitude = 18.0052025; student.longitude = -76.7499753; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Lab2" ;student.latitude = 18.005901; student.longitude = -76.749803; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Software Engineering Lab" ;student.latitude = 18.0064435; student.longitude = -76.7485883; studentRepo.insert(student);

        studentRepo = new StudentRepo(this); student.name="Room 1" ;student.latitude = 18.006131; student.longitude = -76.749020; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Room 2" ;student.latitude = 18.006131; student.longitude = -76.749020; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Room 3" ;student.latitude = 18.006131; student.longitude = -76.749020; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Room 4" ;student.latitude = 18.006131; student.longitude = -76.749020; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Room 5" ;student.latitude = 18.006131; student.longitude = -76.749020; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Room 6" ;student.latitude = 18.006131; student.longitude = -76.749020; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Museum" ;student.latitude = 18.006131; student.longitude = -76.749020; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Marine Geology Unit" ;student.latitude = 18.006016; student.longitude = -76.749323; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Lab 1" ;student.latitude = 18.006131; student.longitude = -76.749020; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Lab 2" ;student.latitude = 18.006131; student.longitude = -76.749020; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Lab 3" ;student.latitude = 18.006131; student.longitude = -76.749020; studentRepo.insert(student);

        studentRepo = new StudentRepo(this); student.name="Life Science Main Office" ;student.latitude = 18.006182; student.longitude = -76.750341; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Life Science Lecture Theater 1" ;student.latitude = 18.006182; student.longitude = -76.750342; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Life Science Lecture Theater 2" ;student.latitude = 18.006182; student.longitude = -76.750343; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Life Science Lecture Theater 3" ;student.latitude = 18.006182; student.longitude = -76.750340; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Life Science Lecture Theater 4" ;student.latitude = 18.006182; student.longitude = -76.750340; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Life Science Lecture Theater 5" ;student.latitude = 18.006182; student.longitude = -76.750340; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Aquatic Sciences Lab" ;student.latitude = 18.006182; student.longitude = -76.750340; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Block A" ;student.latitude = 18.006182; student.longitude = -76.750348; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Block B" ;student.latitude = 18.006182; student.longitude = -76.750346; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Block C" ;student.latitude = 18.006182; student.longitude = -76.750344; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Block D" ;student.latitude = 18.006182; student.longitude = -76.750345; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Block E" ;student.latitude = 18.006182; student.longitude = -76.750345; studentRepo.insert(student);

        studentRepo = new StudentRepo(this); student.name="Physics Main Office" ;student.latitude = 18.004836	; student.longitude = -76.748823; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Phys A" ;student.latitude = 18.004928; student.longitude = -76.749047; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Phys B" ;student.latitude = 18.0049536; student.longitude = -76.7490518; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Phys C" ;student.latitude = 18.0049459; student.longitude = -76.7490336; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Phys D" ;student.latitude = 18.004500; student.longitude = -76.749037; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Electronics Lab" ;student.latitude = 18.004620; student.longitude = -76.748906; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Virtual Lab" ;student.latitude = 18.004586; student.longitude = -76.749019; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Earthquake Unit" ;student.latitude = 18.004586; student.longitude = -76.749019; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Electronics Unit" ;student.latitude = 18.004586; student.longitude = -76.749019; studentRepo.insert(student);

        studentRepo = new StudentRepo(this); student.name="Engineering Main Office" ;student.latitude = 18.004615; student.longitude = -76.750417; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Engineering Lecture Theater 1" ;student.latitude = 18.0051555; student.longitude = -76.7497084; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Engineering Lecture Theater 2" ;student.latitude = 18.0051632; student.longitude = -76.7497196; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Engineering Lecture Theater 3" ;student.latitude = 18.0051548; student.longitude = -76.749703; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Engineering Lecture Theater 4" ;student.latitude = 18.0050984; student.longitude = -76.7496972; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Engineering Lecture Theater 5" ;student.latitude = 18.0051141; student.longitude = -76.7496359; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Electronics Engineering Lab" ;student.latitude = 18.0051221; student.longitude = -76.7495705; studentRepo.insert(student);

        studentRepo = new StudentRepo(this); student.name="Humanities Gazebos"	;student.latitude = 18.0048848	; student.longitude = -76.7462619; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Nursing Gazebos" ;student.latitude = 18.003946; student.longitude = -76.744698; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Science and Tech Gazebos" ;student.latitude = 18.005288; student.longitude = -76.748512; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Law Gazebos" ;student.latitude = 18.008171; student.longitude = -76.748520; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Mona School of Business Gazebos" ;student.latitude = 18.008303; student.longitude = -76.748074; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Commuting Lounge Gazebos";student.latitude = 18.0051758; student.longitude = -76.745592; studentRepo.insert(student);

        studentRepo = new StudentRepo(this); student.name="Chancellor Hall Office" ;student.latitude = 18.005720; student.longitude = -76.744179; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Block Aye" ;student.latitude = 18.005982; student.longitude = -76.744221; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Block Runci" ;student.latitude = 18.006530; student.longitude = -76.744502; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Block Che'" ;student.latitude = 18.006026; student.longitude = -76.743852; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Block Dynamite" ;student.latitude = 18.006647; student.longitude = -76.743992; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Block X" ;student.latitude = 18.005619; student.longitude = -76.744567; studentRepo.insert(student);

        studentRepo = new StudentRepo(this); student.name="ABC Hall Office";student.latitude = 18.012959; student.longitude = -76.743555; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="ABC Block A";student.latitude = 18.013131; student.longitude = -76.743253; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="ABC Block B";student.latitude = 18.013131; student.longitude = -76.743253; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="ABC Block C";student.latitude = 18.013131; student.longitude = -76.743253; studentRepo.insert(student);

        studentRepo = new StudentRepo(this); student.name="Taylor Hall Office";student.latitude = 18.007293; student.longitude = -76.744232; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Block Attica";student.latitude = 18.008012; student.longitude = -76.744153; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Block Butchers";student.latitude = 18.007970; student.longitude = -76.744796; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Block Roosters";student.latitude = 18.007495; student.longitude = -76.744007; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Block Stallion";student.latitude = 18.007360; student.longitude = -76.744486; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Block G";student.latitude = 18.008415; student.longitude = -76.744475; studentRepo.insert(student);

        studentRepo = new StudentRepo(this); student.name="Tower Hall Office";student.latitude = 18.007665; student.longitude = -76.742904; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Tower 1";student.latitude = 18.008046; student.longitude = -76.743466; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Tower 2";student.latitude = 18.007802; student.longitude = -76.743489; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Tower 3";student.latitude = 18.007665; student.longitude = -76.742904; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Tower 4";student.latitude = 18.007243; student.longitude = -76.743589; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Tower 5";student.latitude = 18.006986; student.longitude = -76.743609; studentRepo.insert(student);

        studentRepo = new StudentRepo(this); student.name="Mary Seacole Hall Office";student.latitude = 18.004750; student.longitude = -76.745292; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Jade Block";student.latitude = 18.004024; student.longitude = -76.745460; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Great Block";student.latitude = 18.004055; student.longitude = -76.745089; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Hype Block";student.latitude = 18.004349; student.longitude = -76.745482; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Crowd Block";student.latitude = 18.004544; student.longitude = -76.745215; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Seacole Block Dynamite";student.latitude = 18.004558; student.longitude = -76.745024; studentRepo.insert(student);

        studentRepo = new StudentRepo(this); student.name="Irvin Hall Office";student.latitude = 18.006083; student.longitude = -76.742349; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Block Alpha";student.latitude = 18.006198; student.longitude = -76.742466; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Block Bears";student.latitude = 18.006432; student.longitude = -76.742841; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Block Chalise";student.latitude = 18.006746; student.longitude = -76.742501; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Freshers Block";student.latitude = 18.007306; student.longitude = -76.742814; studentRepo.insert(student);

        studentRepo = new StudentRepo(this); student.name="Rex Hall Office";student.latitude = 18.002636; student.longitude = -76.741620; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Cluster 1";student.latitude = 18.001685; student.longitude = -76.741456; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Cluster 2";student.latitude = 18.001798; student.longitude = -76.741715; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Cluster 3";student.latitude = 18.002032; student.longitude = -76.742028; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Cluster 4";student.latitude = 18.002473; student.longitude = -76.742419; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Cluster 5";student.latitude = 18.002892; student.longitude = -76.742475; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Cluster 6";student.latitude = 18.003125; student.longitude = -76.742189; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Cluster 7";student.latitude = 18.003683; student.longitude = -76.742070; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Cluster 8";student.latitude = 18.003161; student.longitude = -76.741626; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Cluster 9";student.latitude = 18.003629; student.longitude = -76.741418; studentRepo.insert(student);

        studentRepo = new StudentRepo(this); student.name="Preston Hall Office";student.latitude = 18.000490; student.longitude = -76.742613; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Cluster Amsterdam";student.latitude = 18.001388; student.longitude = -76.742028; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Cluster Belqique";student.latitude = 18.001317; student.longitude = -76.742538; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Cluster Burgplatz";student.latitude = 18.001424; student.longitude = -76.742843; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Cluster Einheit";student.latitude = 17.999858; student.longitude = -76.741872; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Cluster Italia";student.latitude = 18.000169; student.longitude = -76.741829; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Cluster Los Matadores";student.latitude = 18.000516; student.longitude = -76.742017; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Cluster Sherwood Manor";student.latitude = 17.999944; student.longitude = -76.742940; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Cluster Regensen";student.latitude = 17.999893; student.longitude = -76.742189; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Cluster Olympia";student.latitude = 18.000970; student.longitude = -76.742350; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Cluster La Maison";student.latitude = 18.001742; student.longitude = -76.742551; studentRepo.insert(student);

        studentRepo = new StudentRepo(this); student.name="138 Office";student.latitude = 18.002074; student.longitude = -76.744160; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="T 1";student.latitude = 18.002247; student.longitude = -76.744059; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="T 2";student.latitude = 18.002166; student.longitude = -76.743909; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="T 3";student.latitude = 18.002166; student.longitude = -76.743909; studentRepo.insert(student);

        studentRepo = new StudentRepo(this); student.name="New Post Grad";student.latitude = 18.000566; student.longitude = -76.745873; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Old PostGrad";student.latitude = 18.001680; student.longitude = -76.743406; studentRepo.insert(student);

        studentRepo = new StudentRepo(this); student.name="Humanities Faculty Main Office" ;student.latitude = 18.0052098; student.longitude = -76.7461714; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="N 1" ;student.latitude = 18.0051723; student.longitude = -76.746152; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="N 2" ;student.latitude = 18.0051884; student.longitude = -76.746152; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="N 3" ;student.latitude = 18.0051908; student.longitude = -76.7461325; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="N 4" ;student.latitude = 18.0052132; student.longitude = -76.7461167; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="NELT" ;student.latitude = 18.0057788; student.longitude = -76.7466837; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Caribbean Studies" ;student.latitude = 18.0052622; student.longitude = -76.7461434; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="English Lit" ;student.latitude = 18.0052255; student.longitude = -76.7461625; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="School of Education" ;student.latitude = 18.005354; student.longitude = -76.745676; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Modern Language and literature" ;student.latitude = 18.004788; student.longitude = -76.7467708; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Library and Information Studies" ;student.latitude = 18.0047863	; student.longitude = -76.746868; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="School of Education Lecture Theater" ;student.latitude = 18.005354; student.longitude = -76.745676; studentRepo.insert(student);

        studentRepo = new StudentRepo(this); student.name="Law Faculty Main Office" ;student.latitude = 18.008244; student.longitude = -76.748480; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Lecture Room 1A" ;student.latitude = 18.008244; student.longitude = -76.748480; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Lecture Room 2B" ;student.latitude = 18.008244; student.longitude = -76.748480; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Lecture Room 1" ;student.latitude = 18.008244; student.longitude = -76.748480; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Lecture Room 2" ;student.latitude = 18.008171; student.longitude = -76.748520; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Lecture Room 3" ;student.latitude = 18.008244; student.longitude = -76.748480; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Seminar Room 1" ;student.latitude = 18.008244; student.longitude = -76.748480; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Seminar Room 2" ;student.latitude = 18.008171; student.longitude = -76.748520; studentRepo.insert(student);

        studentRepo = new StudentRepo(this); student.name="Science Lecture Theater" ;student.latitude = 18.005187; student.longitude = -76.749936; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Pre Clinical Theater1" ;student.latitude = 18.005139; student.longitude = -76.749792; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Inter-faculty Lecture Theater" ;student.latitude = 18.005653; student.longitude = -76.748783; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Biology Lecture Theater" ;student.latitude = 18.006084; student.longitude = -76.750487; studentRepo.insert(student);

        studentRepo = new StudentRepo(this); student.name="Social Science Lecture Theater" ;student.latitude = 18.006695; student.longitude = -76.747406; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="GLT 1" ;student.latitude = 18.007913; student.longitude = -76.747629; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="GLT 2" ;student.latitude = 18.007913; student.longitude = -76.747629; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="GLT 3" ;student.latitude = 18.007913; student.longitude = -76.747629; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="SR  1 - 23" ;student.latitude = 18.0071234; student.longitude = -76.7471699; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Psychology Unit" ;student.latitude = 18.007913; student.longitude = -76.747629; studentRepo.insert(student);

        studentRepo = new StudentRepo(this); student.name="New Arts Block" ;student.latitude = 18.0049568; student.longitude = -76.7464424; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Life Science Main Office" ;student.latitude = 18.006182; student.longitude = -76.750340; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Life Science Lecture Theater1" ;student.latitude = 18.006182; student.longitude = -76.750340; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Life Science Lecture Theater2" ;student.latitude = 18.006182; student.longitude = -76.750340; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Life Science Lecture Theater3" ;student.latitude = 18.006182; student.longitude = -76.750340; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Life Science Lecture Theater4" ;student.latitude = 18.006182; student.longitude = -76.750340; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Life Science Lecture Theater5";student.latitude = 18.006182; student.longitude = -76.750340; studentRepo.insert(student);

        studentRepo = new StudentRepo(this); student.name="Math Main Office" ;student.latitude = 18.004714; student.longitude = -76.749561; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Math Lecture theater 1" ;student.latitude = 18.004714; student.longitude = -76.749561; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Math Lecture theater 2" ;student.latitude = 18.004714; student.longitude = -76.749561; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Math Lecture theater 3"; student.longitude = 18.004748; student.longitude = -76.749460; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Math Lab"; student.longitude = 18.004748; student.longitude = -76.749460; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Postgrad Room 1"; student.longitude = 18.004748; student.longitude = -76.749460; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Postgrad Room 2"; student.longitude = 18.004748; student.longitude = -76.749460; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Postgrad Room 3"; student.longitude = 18.004748; student.longitude = -76.749460; studentRepo.insert(student);

        studentRepo = new StudentRepo(this); student.name="Main Library" ;student.latitude = 18.006332; student.longitude = -76.745874; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Science Library" ;student.latitude = 18.005309; student.longitude = -76.749467; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Social Science Library" ;student.latitude = 18.006381; student.longitude = -76.748001; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Med Library" ;student.latitude = 18.010876; student.longitude = -76.745790; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Law Library" ;student.latitude = 18.008171	; student.longitude = -76.748520; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Old Library" ;student.latitude = 18.000991; student.longitude = -76.744840; studentRepo.insert(student);

        studentRepo = new StudentRepo(this); student.name="Med Faculty Main Office" ;student.latitude = 18.009653; student.longitude = -76.746733; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Child and Adolescents" ;student.latitude = 18.009653; student.longitude = -76.746733; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Community Health Psychiatry" ;student.latitude = 18.009653; student.longitude = -76.746733; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Medicine" ;student.latitude = 18.009653; student.longitude = -76.746733; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Micro Biology" ;student.latitude = 18.009653; student.longitude = -76.746733; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Surgery" ;student.latitude = 18.009653; student.longitude = -76.746733; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="School of Nursing" ;student.latitude = 18.003890; student.longitude = -76.744349; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Radiology" ;student.latitude = 18.009653; student.longitude = -76.746733; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Anaesthesia" ;student.latitude = 18.009653; student.longitude = -76.746733; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Intensive Care" ;student.latitude = 18.009653; student.longitude = -76.746733; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Obstetrics Gynaecology" ;student.latitude = 18.009653; student.longitude = -76.746733; studentRepo.insert(student);

        studentRepo = new StudentRepo(this); student.name="Kentucky Fried Chicken" ;student.latitude = 18.006140; student.longitude = -76.744805; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="The Spot" ;student.latitude = 18.000793; student.longitude = -76.743276; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Yao" ;student.latitude = 18.000793; student.longitude = -76.743276; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Juici Patties" ;student.latitude = 18.005114; student.longitude = -76.748565; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Pages Cafe'" ;student.latitude = 18.006234; student.longitude = -76.744835; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="BeeHive" ;student.latitude = 18.004488; student.longitude = -76.746364; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Dukunoo Deli" ;student.latitude = 18.007227; student.longitude = -76.747139; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Bucks" ;student.latitude = 18.007630; student.longitude = -76.748592; studentRepo.insert(student);

        studentRepo = new StudentRepo(this); student.name="Faculty Office" ;student.latitude = 18.0071244; student.longitude = -76.7471703; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Hotel and Tourism" 	;student.latitude = 18.007125; student.longitude = -76.7471669; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Economics" ;student.latitude = 18.006531; student.longitude = -76.746878; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Government" ;student.latitude = 18.006531; student.longitude = -76.746878; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Mona School of Business North" ;student.latitude = 18.007952; student.longitude = -76.747375; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Mona School of Business South" ;student.latitude = 18.006381; student.longitude = -76.748001; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Sociology and Social Work" 	;student.latitude = 18.0071231; student.longitude = -76.7471676; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Psychology Unit" ;student.latitude = 18.0071241; student.longitude = -76.7471698; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Institute for Gender and Development" 	;student.latitude = 18.0071241; student.longitude = -76.7471698; studentRepo.insert(student);

        studentRepo = new StudentRepo(this); student.name="Main Library Stall" ;student.latitude = 18.005484; student.longitude = -76.745527; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Humanities Stall" 	;student.latitude = 18.0050561; student.longitude = -76.7459129; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Social Science Stall" ;student.latitude = 18.0065191; student.longitude = -76.7469276; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Science and Tech Stall 1" ;student.latitude = 18.0049263; student.longitude = -76.74912; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="Science and Tech Stall 2" ;student.latitude = 18.0049442; student.longitude = -76.74912; studentRepo.insert(student);

        studentRepo = new StudentRepo(this); student.name="UWI Health Center";student.latitude = 18.004315; student.longitude = -76.743712; studentRepo.insert(student);
        studentRepo = new StudentRepo(this); student.name="University Hospital";student.latitude = 18.011353; student.longitude = -76.744420; studentRepo.insert(student);

    }

}

