package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import org.apache.commons.io.FileUtils.writeLines
import org.apache.commons.io.FilenameUtils
import org.apache.commons.io.IOUtils.writeLines
//import org.apache.commons.io.FilenameUtils
//import org.apache.commons.io.IOUtils.writeLines
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                listOfTasks.removeAt(position)
                adapter.notifyDataSetChanged()

                saveItems()
            }
        }

   //     findViewById<Button>(R.id.button).setOnClickListener{
   //         Log.i("Muhi", "was clicked on")
        //    }

       // listOfTasks.add("Do Laundry")
        //listOfTasks.add("Go For a Walk")

        loadItems()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
         adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        findViewById<Button>(R.id.button).setOnClickListener{
            val userInputtedTask = findViewById<EditText>(R.id.addTaskField).text.toString()
            listOfTasks.add(userInputtedTask)
            adapter.notifyItemInserted(listOfTasks.size -1)
            findViewById<EditText>(R.id.addTaskField).setText("")

            saveItems()
        }
    }


    fun getDataFile() : File {

        return File(filesDir,"data.txt")
    }
    fun loadItems() {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException : IOException){
            ioException.printStackTrace()
        }
    }

    fun saveItems (){
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException : IOException){
            ioException.printStackTrace()
        }
    }

}