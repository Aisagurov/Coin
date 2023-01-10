package suvorov.coin.ui.portfolio

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import suvorov.coin.R
import suvorov.coin.databinding.FragmentPortfolioBinding
import suvorov.coin.ui.base.BaseFragment
import suvorov.coin.ui.currencies.adapter.clicklistener.CurrenciesClickListener
import suvorov.coin.ui.portfolio.adapter.PortfolioAdapter
import suvorov.coin.util.ShowHelper.showToast

class PortfolioFragment:
    BaseFragment<FragmentPortfolioBinding>(FragmentPortfolioBinding::inflate),
    CurrenciesClickListener
{
    private val viewModel: PortfolioViewModel by viewModel()
    private val portfolioAdapter by lazy { PortfolioAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        observeViewModel()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun observeViewModel() {
        viewModel.portfolioCurrencies.observe(viewLifecycleOwner) {
            portfolioAdapter.submitList(it)
            binding.portfolioTextView.visibility = if(it.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun initView() {
        binding.portfolioRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = portfolioAdapter
            deleteItemSwipe(this)
        }
    }

    override fun onCurrencyClick(id: String) {
        findNavController().navigate(
            PortfolioFragmentDirections.actionPortfolioFragmentToCurrencyFragment(id))
    }

    private fun deleteItemSwipe(recyclerView: RecyclerView) {

        val deleteIcon = ContextCompat.getDrawable(requireContext(), R.drawable.delete_icon)!!

        val colorDrawableBackground =
            ColorDrawable(ContextCompat.getColor(requireContext(), R.color.granite))

        val itemTouchHelperCallback =
            object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = portfolioAdapter.currentList[viewHolder.adapterPosition]
                viewModel.updatePortfolioStatus(item.id)
                showToast(requireContext(),
                    "${getString(R.string.coin)} ${item.name} ${getString(R.string.removed_from_portfolio)}"
                )
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val iconMarginVertical = (itemView.height - deleteIcon.intrinsicHeight) / 2

                if (dX > 0) {
                    colorDrawableBackground.setBounds(
                        itemView.left,
                        itemView.top,
                        dX.toInt(),
                        itemView.bottom
                    )

                    deleteIcon.setBounds(
                        itemView.left + iconMarginVertical,
                        itemView.top + iconMarginVertical,
                        itemView.left + iconMarginVertical + deleteIcon.intrinsicWidth,
                        itemView.bottom - iconMarginVertical
                    )
                } else {
                    colorDrawableBackground.setBounds(
                        itemView.right + dX.toInt(),
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                    )
                    deleteIcon.setBounds(
                        itemView.right - iconMarginVertical - deleteIcon.intrinsicWidth,
                        itemView.top + iconMarginVertical,
                        itemView.right - iconMarginVertical,
                        itemView.bottom - iconMarginVertical
                    )
                }

                colorDrawableBackground.draw(c)

                if (dX > 0)
                    c.clipRect(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                else
                    c.clipRect(
                        itemView.right + dX.toInt(),
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                    )

                deleteIcon.draw(c)

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}