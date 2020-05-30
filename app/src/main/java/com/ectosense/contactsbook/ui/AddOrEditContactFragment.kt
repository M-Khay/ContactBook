package com.ectosense.contactsbook.ui

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ectosense.contactsbook.R
import com.ectosense.contactsbook.db.Person
import kotlinx.android.synthetic.main.add_or_edit_contact_fragment.*
import kotlinx.android.synthetic.main.add_or_edit_contact_fragment.view.*


class AddOrEditContactFragment : Fragment() {

    val READ_EXST = 0x4
    private val REQUEST_GALLERY_PHOTO: Int = 0
    private lateinit var contactViewModel: ContactViewModel
    private lateinit var actionBarListener: ActionBarCallBack
    private var isNewContact: Boolean = true
    private var imageUri: String? = null

    companion object {
        const val IS_NEW_CONTACT = "isNewContact"

        @JvmStatic
        fun newInstance(isNewContact: Boolean) = AddOrEditContactFragment().apply {
            arguments = Bundle().apply {
                putBoolean(IS_NEW_CONTACT, isNewContact)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ActionBarCallBack && context is ContactListFragment.ChangeFragmentListener) {
            actionBarListener = context
        } else {
            throw ClassCastException("$context must implement ActionBarCallBack")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_or_edit_contact_fragment, container, false)
        setHasOptionsMenu(true)
        contactViewModel = ViewModelProvider(requireActivity()).get(ContactViewModel::class.java)

        arguments?.getBoolean(IS_NEW_CONTACT)?.let {
            isNewContact = it
        }
        if (!isNewContact) {
            val person = contactViewModel.selectedContact.value
            person?.let {
                view.first_name_et.setText(it.firstName)
                view.lastname_et.setText(it.lastName)
                view.phone_number_et.setText(it.phone)
                view.email_id_et.setText(it.email)
                it.photo?.let { imageUri ->
                    this.imageUri = imageUri
                    showSelectedImage(Uri.parse(imageUri),view.poster)
                }
            }
        }
        view.poster.setOnClickListener {
            askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE, READ_EXST);
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        actionBarListener.showActionBarWithIcon("", true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.add_edit_contact_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.d -> {
                val firstName = first_name_et.text.toString()
                val lastName = lastname_et.text.toString()
                val phone = phone_number_et.text.toString()
                val email = email_id_et.text.toString()

                if (validateData(firstName, lastName, phone, email)) {
                    saving_content.visibility = View.VISIBLE
                    val result = contactViewModel.addOrEditContact(
                        person = Person(
                            firstName,
                            lastName,
                            email,
                            phone,
                            false,
                            imageUri
                        ), isNewContact = isNewContact
                    )
                    contactViewModel.selectedContact.value = result
                }
                return true
            }

        }
        return super.onOptionsItemSelected(item)

    }

    private fun validateData(
        firstName: String,
        lastName: String,
        phone: String,
        email: String
    ): Boolean {
        if (firstName.isEmpty() && lastName.isEmpty()) {
            Toast.makeText(
                activity,
                resources.getString(R.string.error_first_last_name),
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        if (phone.isEmpty() || email.isEmpty()) {
            Toast.makeText(
                activity,
                resources.getString(R.string.error_phone_email),
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        return true
    }

    private fun dispatchGalleryIntent() {
        val pickPhoto = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_PHOTO) {
                imageUri = data?.data.toString()
                showSelectedImage(Uri.parse(imageUri),poster)
            }
        }
    }

    private fun showSelectedImage(selectedImage: Uri?, imageView: ImageView) {
        Glide.with(requireActivity())
            .load(selectedImage)
            .apply(
                RequestOptions().centerCrop()
                    .circleCrop()
                    .placeholder(R.mipmap.ic_image_placeholder_round)
            )
            .into(imageView)
    }

    private fun askForPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                requireActivity(), permission
            ) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    permission)) {
                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(permission),
                    requestCode) }
            else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(permission),
                    requestCode
                )
            }
        } else {
            dispatchGalleryIntent()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                permissions[0]
            ) == PackageManager.PERMISSION_GRANTED) {
            when (requestCode) {
                READ_EXST -> {
                    dispatchGalleryIntent()
                }
            }
        }
    }

}
