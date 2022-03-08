package net.tiap.todoappfirebase.customs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException

abstract class CustomAdapter<TipeData, ViewHolder : RecyclerView.ViewHolder>(
    protected var mLayout: Int,
    var mViewHolderClass: Class<ViewHolder>,
    var mModelClass: Class<TipeData>,
    mData: List<TipeData>
) :
    RecyclerView.Adapter<ViewHolder>() {

    private var mArrayData = ArrayList<TipeData>()

    init {
        mArrayData.clear()
        mArrayData.addAll(mData)
    }

    fun addData(data: TipeData) {
        mArrayData.add(data)
        notifyItemInserted(itemCount - 1)
    }

    fun addDataBatch(dataList: List<TipeData>) {
        mArrayData.addAll(dataList)
        notifyItemRangeInserted(itemCount - 1, dataList.size)
    }

    fun setDataBatch(dataList: List<TipeData>) {
        mArrayData = dataList as ArrayList<TipeData>
        notifyDataSetChanged()
    }

    fun insertData(position: Int, data: TipeData) {
        mArrayData.add(position, data)
        notifyItemInserted(position)
        notifyItemRangeChanged(position, mArrayData.size)
    }

    fun removeData(position: Int) {
        mArrayData.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mArrayData.size)
    }

    fun removeAll() {
        mArrayData.clear()
        notifyDataSetChanged()
    }

    fun getmData(): List<TipeData> {
        return mArrayData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(mLayout, parent, false) as ViewGroup
        return try {
            val constructor: Constructor<ViewHolder> =
                mViewHolderClass.getConstructor(View::class.java)
            constructor.newInstance(view)
        } catch (e: NoSuchMethodException) {
            throw RuntimeException(e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        } catch (e: InstantiationException) {
            throw RuntimeException(e)
        } catch (e: InvocationTargetException) {
            throw RuntimeException(e)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = getItem(position)
        bindView(holder, model, position)
    }

    protected abstract fun bindView(holder: ViewHolder, model: TipeData, position: Int)

    private fun getItem(position: Int): TipeData {
        return mArrayData[position]
    }

    override fun getItemCount(): Int {
        return mArrayData.size
    }
}