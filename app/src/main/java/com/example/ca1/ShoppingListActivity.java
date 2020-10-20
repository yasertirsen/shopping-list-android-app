package com.example.ca1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.ca1.MainActivity.USERNAME_KEY;

public class ShoppingListActivity extends AppCompatActivity {
    private TextView welcome;
    private EditText etItem;
    private EditText etPrice;
    private Button addItem;
    private Button removeItem;
    private FloatingActionButton fabAddItem;
    private ListView lvShoppingList;
    private List<Item> items = new ArrayList<Item>();
    private ArrayAdapter<Item> arrayAdapter;
    public static String TOTAL = "TOTAL";
    public static String NO_OF_ITEMS = "NO_OF_ITEMS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        welcome = (TextView)findViewById(R.id.tvWelcome);
        etItem = (EditText)findViewById(R.id.etItem);
        etPrice = (EditText)findViewById(R.id.etPrice);
        lvShoppingList = (ListView)findViewById(R.id.lvShoppingList);
        addItem = (Button)findViewById(R.id.btnAddItem);
        removeItem = (Button)findViewById(R.id.btnRemoveItem);
        fabAddItem = (FloatingActionButton)findViewById(R.id.fabAddItem);

        Intent intent = getIntent();
        String username = intent.getStringExtra(USERNAME_KEY);

        if(username != null)
            welcome.setText("Welcome! " + username);

        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etItem.setVisibility(View.VISIBLE);
                etPrice.setVisibility(View.VISIBLE);
                addItem.setVisibility(View.VISIBLE);
                lvShoppingList.setVisibility(View.GONE);
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // set to add only unique item name
                    boolean inList = false;
                    for(Item item : items) {
                        if(item.getName().equalsIgnoreCase(etItem.getText().toString()))
                            inList = true;
                    }

                    Item item = new Item(etItem.getText().toString(), Double.parseDouble(etPrice.getText().toString()));

                    if(!inList)
                        add(item);
                    else
                        Toast.makeText(getApplicationContext(), "Item is already in the list!", Toast.LENGTH_SHORT).show();

                    etItem.setText("");
                    etPrice.setText("");
                    etItem.setVisibility(View.GONE);
                    etPrice.setVisibility(View.GONE);
                    addItem.setVisibility(View.GONE);
                    hideKeybaord(v);

                } catch (NumberFormatException nfe) {
                    Toast.makeText(getApplicationContext(), "Price entered is not valid!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Make sure item and price are entered", Toast.LENGTH_SHORT).show();
                }
            }
        });

        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item deleteItem = null;

                for(Item item : items) {
                    if(item.getName().equalsIgnoreCase(etItem.getText().toString()))
                        deleteItem = item;
                }
                if(deleteItem != null){
                    items.remove(deleteItem);
                    Toast.makeText(getApplicationContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Item is not in the list!", Toast.LENGTH_SHORT).show();

                etItem.setVisibility(View.GONE);
                removeItem.setVisibility(View.GONE);
                etItem.setText("");

                hideKeybaord(v);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        if(id==R.id.action_checkout) {
            if(items.isEmpty())
                Toast.makeText(getApplicationContext(), "Shopping list is empty!", Toast.LENGTH_SHORT).show();
            else {
                double total = 0;
                for(Item item : items) {
                    total+=item.getPrice();
                }
                Intent intent = new Intent(getApplicationContext(), Checkout.class);
                intent.putExtra(TOTAL, total);
                intent.putExtra(NO_OF_ITEMS, items.size());
                startActivity(intent);
            }
            return true;
        }

        else if(id==R.id.action_remove) {
            if(items.isEmpty())
                Toast.makeText(getApplicationContext(), "Shopping list is empty!", Toast.LENGTH_SHORT).show();
            else {
                etItem.setVisibility(View.VISIBLE);
                removeItem.setVisibility(View.VISIBLE);
                etPrice.setVisibility(View.GONE);
                addItem.setVisibility(View.GONE);
                lvShoppingList.setVisibility(View.GONE);
            }
            return true;
        }

        else if(id==R.id.action_view) {
            if(items.isEmpty())
                Toast.makeText(getApplicationContext(), "Shopping list is empty!", Toast.LENGTH_SHORT).show();
            else {
                etItem.setVisibility(View.GONE);
                etPrice.setVisibility(View.GONE);
                addItem.setVisibility(View.GONE);
                removeItem.setVisibility(View.GONE);
                arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, items);
                lvShoppingList.setAdapter(arrayAdapter);
                lvShoppingList.setVisibility(View.VISIBLE);
            }
            return true;
        }
        return true;
    }

    public void add(Item item) {
        items.add(item);
        Toast.makeText(getApplicationContext(), "Item Added", Toast.LENGTH_SHORT).show();
    }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }
}
