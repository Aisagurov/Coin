package suvorov.coin.data.mapper

interface BaseMapper<in A, out B> {
    fun map(item: A): B
}