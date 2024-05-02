package com.lizw.core_apis.thirdpartlibs.room

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.lizw.core_apis.databinding.ActivityRoomBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "RoomActivity"
    }

    private val binding by lazy { ActivityRoomBinding.inflate(layoutInflater) }

    private val viewModel: RoomViewModel by viewModels { RoomViewModel.Factory }

    private var i = 0
    private var person: Person? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnInsert.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                i++
                person = Person(i, i.toString() + "", i.toDouble(), i % 2 == 0)
                viewModel.insert(person!!)
            }
        }
        binding.btnDelete.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.delete(Person(1, "1", 1.0, true))
            }
        }
        binding.btnDeleteById.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.deleteById(3)
            }
        }
        binding.btnUpdate.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.update(Person(1, "1", 1.0, true))
            }
        }
        binding.btnQuery.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val persons: List<Person>? = viewModel.query()
                if (!persons.isNullOrEmpty()) {
                    for (p in persons) {
                        Log.e(TAG, "query all: $p")
                    }
                }
            }
        }
        binding.btnQueryById.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val person: Person? = viewModel.queryById(2)
                if (person != null) {
                    Log.e(TAG, "queryById: $person")
                }
            }
        }
    }

}