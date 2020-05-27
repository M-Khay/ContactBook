package com.ectosense.contactsbook.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ectosense.contactsbook.R
import com.ectosense.contactsbook.db.Person
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), ContactListFragment.ChangeFragmentListener {

    private val contactViewModel by viewModel<ContactViewModel>()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val fm = supportFragmentManager
            fm.beginTransaction()
                .add(R.id.container, ContactListFragment())
                .commit()
        }

        contactViewModel.selectedContact.observe(this, selectedContactObserver)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun changeFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, AddOrEditContactFragment())
            .addToBackStack(null)
            .commit()
    }

    private val selectedContactObserver = Observer<Person> {
        it?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ContactDetailsFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
