package com.android.sipp.utils

import com.android.sipp.R
import com.android.sipp.model.History
import com.android.sipp.model.ItemRecycle
import com.android.sipp.model.News

object Dummy {
    fun generateNews(): ArrayList<News>{
        val news = arrayListOf<News>()
        news.add(News(
            "detikFinance",
            "Rabu, 09 Jun 2021 18:53 WIB",
            "Ini Dia Rumah Kelola Sampah Berkelanjutan di Sultra",
            "PT Pelayaran Nasional Indonesia atau PT PELNI (Persero) kembali membangun Rumah Kelola Sampah (RKS) di Sulawesi Tenggara. Seperti apa sih penampakannya?",
            "https://finance.detik.com/foto-bisnis/d-5600018/ini-dia-rumah-kelola-sampah-berkelanjutan-di-sultra?_ga=2.157798131.878852849.1623329455-166294528.1622648571",
            R.drawable.news_1))
        news.add(News(
            "detikNews",
            "Rabu, 09 Jun 2021 17:05 WIB",
            "Tumpukan Sampah Mengular di Pinggir Jalanan Asahan Mulai Berkurang",
            "Tumpukan sampah yang sempat mengular di pinggir jalanan di Kisaran, Asahan, Sumatera Utara (Sumut), mulai berkurang. Sampah tak lagi terlihat bertumpuk dan mengular di pinggir jalan.",
            "https://news.detik.com/berita/d-5599442/tumpukan-sampah-mengular-di-pinggir-jalanan-asahan-mulai-berkurang?_ga=2.77180636.878852849.1623329455-166294528.1622648571",
            R.drawable.news_2))
        news.add(News(
            "detikNews",
            "Selasa, 08 Jun 2021 20:59 WIB",
            "Nestapa Sampah Plastik di Tengah Hari Laut Sedunia",
            "8 Juni diperingati sebagai Hari Laut Sedunia. Ironisnya di tengah Hari Laut Sedunia ini polemik sampah plastik masih menjadi permasalahan seluruh negara dunia.",
            "https://news.detik.com/foto-news/d-5598369/nestapa-sampah-plastik-di-tengah-hari-laut-sedunia/12",
            R.drawable.news_3))
        news.add(News(
            "detikNews",
            "Selasa, 08 Jun 2021 19:35 WIB",
            "Waduh, Lubang Bekas Tambang di Cilegon Kini Jadi 'Danau' Sampah",
            "Lubang bekas tambang di Cilegon, Banten ini kini berubah fungsi dipenuhi oleh sampah yang diduga berasal dari mobil pikap yang biasa mengangkut sampah warga.",
            "https://news.detik.com/foto-news/d-5598301/waduh-lubang-bekas-tambang-di-cilegon-kini-jadi-danau-sampah/3",
            R.drawable.news_4))
        news.add(News(
            "detikNews",
            "Selasa, 08 Jun 2021 16:47 WIB",
            "Heboh Lubang Bekas Tambang di Cilegon Jadi 'Danau' Sampah",
            "Lubang bekas tambang di Cilegon, Banten, direklamasi menggunakan sampah. Sampah diduga berasal dari mobil pikap yang biasa mengangkut sampah warga.",
            "https://news.detik.com/berita/d-5598024/heboh-lubang-bekas-tambang-di-cilegon-jadi-danau-sampah?_ga=2.122661186.878852849.1623329455-166294528.1622648571",
            R.drawable.news_5))
        news.add(News(
            "detikNews",
            "Selasa, 08 Jun 2021 12:36 WIB",
            "Tumpukan Sampah Mengular di Asahan Tiap Sore Diangkut, Pagi Numpuk Lagi",
            "Tumpukan sampah yang mengular di Jalan Sutan Syahrir, Kisaran, Asahan, Sumatera Utara, tak ada habis-habisnya. Petugas kebersihan mengatakan sampah di lokasi itu tiap sore hari diangkut, tapi pada paginya selalu numpuk lagi.",
            "https://news.detik.com/berita/d-5597582/tumpukan-sampah-mengular-di-asahan-tiap-sore-diangkut-pagi-numpuk-lagi?_ga=2.122661186.878852849.1623329455-166294528.1622648571",
            R.drawable.news_6))
        news.add(News(
            "detikNews",
            "Senin, 31 Mei 2021 08:44 WIB",
            "Seorang Pemulung Tewas Tertimbun Gunungan Sampah di TPA Makassar",
            "Seorang pemulung tewas setelah tertimbun gunungan sampah di TPA Tamangapa, Makassar, Sulawesi Selatan. Perempuan ini tewas saat hendak memilah sampah di tumpukan sampah dan barang bekas.",
            "https://news.detik.com/berita/d-5587924/seorang-pemulung-tewas-tertimbun-gunungan-sampah-di-tpa-makassar?_ga=2.81306206.878852849.1623329455-166294528.1622648571",
            R.drawable.news_7))
        news.add(News(
            "detikNews",
            "Kamis, 20 Mei 2021 21:30 WIB",
            "Ini Biang Keladi Banjir di Cilebut Bogor",
            "Sungai Kali Baru di kawasan Cilebut, Kabupaten Bogor, Jawa Barat, tersumbat akibat penumpukan sampah.",
            "https://news.detik.com/foto-news/d-5576847/ini-biang-keladi-banjir-di-cilebut-bogor?_ga=2.185495264.878852849.1623329455-166294528.1622648571",
            R.drawable.news_8))
        news.add(News(
            "detikNews",
            "Selasa, 18 Mei 2021 14:37 WIB",
            "Warga Tolak Perluasan Sisi Barat TPST Piyungan Yogya, Ini Alasannya",
            "Sejumlah warga Pedukuhan Ngablak, Kalurahan Sitimulyo, Kapanewon Piyungan, Bantul, Daerah Istimewa Yogyakarta (DIY), tepatnya di sekitar Tempat Pengolahan Sampah Terpadu (TPST) Piyungan menolak perluasan di sisi barat TPST. Alasannya ada permukiman di sisi barat TPST Piyungan tersebut.",
            "https://news.detik.com/berita-jawa-tengah/d-5573445/warga-tolak-perluasan-sisi-barat-tpst-piyungan-yogya-ini-alasannya?_ga=2.22497906.878852849.1623329455-166294528.1622648571",
            R.drawable.news_9))
        news.add(News(
            "detikFinance",
            "Selasa, 18 Mei 2021 09:12 WIB",
            "Eco Green Setor Sampah Botol Plastik Dapat Point",
            "Mendukung kelestarian alam sebuah perusahaan berbasis sosial digital memperkenalkan Mini Collection Point yang dapat ditukarkan dengan point.",
            "https://finance.detik.com/foto-bisnis/d-5573083/eco-green-setor-sampah-botol-plastik-dapat-point?_ga=2.22497906.878852849.1623329455-166294528.1622648571",
            R.drawable.news_10))
        return news
    }

    fun generateItem(): ArrayList<ItemRecycle> {
        val item = ArrayList<ItemRecycle>()
        item.add(
            ItemRecycle(
            "Pot Bunga",
            "Rp10.000",
            5,
            "Pot Bunga",
            R.drawable.item_1
        )
        )
        item.add(ItemRecycle(
            "Tas",
            "Rp10.000",
            3,
            "Tas",
            R.drawable.item_2
        ))
        item.add(ItemRecycle(
            "Pot Gantung",
            "Rp10.000",
            7,
            "Pot Gantung",
            R.drawable.item_3
        ))
        item.add(ItemRecycle(
            "Tempat Pensil",
            "Rp10.000",
            2,
            "Tempat Pensil",
            R.drawable.item_4
        ))
        item.add(ItemRecycle(
            "Robot Mainan",
            "Rp20.000",
            1,
            "Pot Gantung",
            R.drawable.item_5
        ))
        item.add(ItemRecycle(
            "Dompet",
            "Rp30.000",
            2,
            "Tempat Pensil",
            R.drawable.item_6
        ))
        return item
    }

    fun generateHistories(): ArrayList<History> {
        val histories = ArrayList<History>()
        histories.add(
            History(
                "Penjemputan Terjadwal",
                "Selasa, 18 Mei 2021"
            )
        )
        histories.add(
            History(
                "Penjemputan Terjadwal",
                "Jumat, 21 Mei 2021"
            )
        )
        histories.add(
            History(
                "Penjemputan Terjadwal",
                "Selasa, 25 Mei 2021"
            )
        )
        histories.add(
            History(
                "Penjemputan Terjadwal",
                "Jumat, 28 Mei 2021"
            )
        )
        return histories
    }
}






