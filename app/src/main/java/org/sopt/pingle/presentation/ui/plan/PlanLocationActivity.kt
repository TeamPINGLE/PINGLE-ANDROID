package org.sopt.pingle.presentation.ui.plan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityPlanLocationBinding

class PlanLocationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlanLocationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_test, PlanLocationFragment())
            .commit()
    }
}
