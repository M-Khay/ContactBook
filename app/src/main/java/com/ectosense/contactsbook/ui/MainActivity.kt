package com.ectosense.contactsbook.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ectosense.contactsbook.R
import com.ectosense.contactsbook.db.Person
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), ContactListFragment.ChangeFragmentListener {

    private val contactViewModel by viewModel<ContactViewModel>()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        if (savedInstanceState == null) {
            val fm = supportFragmentManager
            fm.beginTransaction()
                .add(R.id.container, ContactListFragment())
                .commit()
        }
        contactViewModel.selectedContact.observe(this, selectedContactObserver)

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
