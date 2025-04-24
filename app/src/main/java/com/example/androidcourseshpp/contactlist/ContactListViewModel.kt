package com.example.androidcourseshpp.contactlist


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContactListViewModel : ViewModel() {
    val contactList : LiveData<List<ContactItem>> get() = mutableContactList
    private val mutableContactList = MutableLiveData<List<ContactItem>>()

    private companion object {
        const val NUM_OF_USERS = 20
        val URLImageList = listOf(
            "https://s3-alpha-sig.figma.com/img/ddfb/d9cc/d761bf491e6218d83cd570ef902b3e61?Expires=1745798400&Key-Pair-Id=APKAQ4GOSFWCW27IBOMQ&Signature=aY5iHE6PluSujjUl3P6dJ3reptYqy~IIORvsMFyhuDG1GYcCX3d3bzyvIfTDhv7WecKNjna3d6LNGIvDbqUgu3fL3-FiGD55-qt-3thsu1Q3VBeQlI9ptTVXVmtTCffqawj81P2v0sSP4k54Ujg0K8hmO4DPelwXOvYJnrcwkBS3hqwXlBCmEyGdwnwCmGVbQ7ZlAR3Bm8wWkXKVRLVSL-YHBEMpnQpanKL9Yll-AAMMO1g-YABsESo6zwZI~jYiWXHWzcv3zVBhs7l-nvW-PuBuRsEEMzQ84LkOBWzr4D1sHP4OkRiqUcxGtHBQLUYu5crbGihqiS2Ar9A-IHAIgg__",
            "https://s3-alpha-sig.figma.com/img/56b7/6275/7dbde4af75876f40325a23557d89e229?Expires=1745798400&Key-Pair-Id=APKAQ4GOSFWCW27IBOMQ&Signature=Hmrb-95X-2hJ6y0lzBFugSYxkgmzET5YApy2G7InNhTxx0jI0A63yrTS2h1YxRiJwb5VswjNOo3ha6zt9GoU6PEn15Ly53KdRkjt0rrTQQBlq6rNqniLmRSoMCnEzXdIUcnPjfPvXWL3U93MsynUQ0emRi7sobXgmQOalJW5tUDSArYZ-3gRBNg0OsDghNsE2znIf5RUXfzhMsOZQlN2q7XEam~yHdt-GwQ5rvFYr3QChJ1jDN20pEc-d69jQcV5V~e00ThFj6kcahNrCKgf0Z3hiLL-isN~gNtHTtZJ5qi8nFE5q8D-X1kFmirkb6xB-oefXqyPbi5-wJJmmLDGWA__",
            "https://s3-alpha-sig.figma.com/img/9cb3/6565/3f014296587cb4bbbdc01ec61b96df35?Expires=1745798400&Key-Pair-Id=APKAQ4GOSFWCW27IBOMQ&Signature=ZpF23Zs3kS7yDIMaez8Wsw391CKOtI-~X~rDWNnJbN7qZL7gh2WjwLbGUOjj-YPsj3kapG-SbZ4ldRnDAKYoAfXI7IHMl4RtnBciRzfrc4VDS5r-fW9DlQCg8qJM3UswbUdBu~vV35lj3jYqwVfLwgA7oAy53KVAbp2FMpsiGTRIXne3CrtTSUS2LPCkzbS3~OSl4e-L6fYOhPVe69UJkL6KKIn2p-tfaZXwJUW07ZOxP-n4VfPHWCLknzlkW7Sdf2VKlj7y1~6mThd~027B3aY3nB-Ppso74NzTkWUmvXFWZmGtXk85oRxKkcYJSvvwVMTgUvgmtUS-vur9hn6DKw__",
            "https://s3-alpha-sig.figma.com/img/7a18/e69e/ca34ee41b570f1948b4e8373ad826041?Expires=1745798400&Key-Pair-Id=APKAQ4GOSFWCW27IBOMQ&Signature=macyaQTqLjBSDWqSRVIo6bHvztH6a4PPF1aCo4DRHyJOxkIvSQCRtmBPAd-ojyUbtKwlOArcFPGOoXsij7EUhZ6U-Znkd2R1AbsOILGEOPoqstXHkok2fySaUgNqsFypk-VWPbnCLSMoOEj124JxHWNRt0NqiJzXtxlVIcD0n9MQPelx0kTq6kPuMv1d1~FWgL4NnX4zhIEkoXwcmqm-WsquaUHYVy-3FCo2~yZFty6NgSQ5uF7cnPv93uIeSml6OgRVnISj6Cy6x0rz4Zbr9BRudHd2Rlbj9EbC~E23mRThjS~H1sThfsKUHx63dEBPrMUd8LkuKqq6vXW6pRIpnQ__",
            "https://s3-alpha-sig.figma.com/img/8e2a/b032/97b0c902250bf8f29057cc70335c63b6?Expires=1745798400&Key-Pair-Id=APKAQ4GOSFWCW27IBOMQ&Signature=kdFTUFIX68mDrncsx1g2~FiCzvQXvwN4e81pYkmYo79bA7eCBjdMx5MD0C4cAPUMuaI6773KczDgMtWbuZMik29aBEZzE7Kv93qilZRm8jJs45rMdffHKFBHTwvXZU~txeqSr2P8R1zPJIkmAnCbCm2TdD1CwVUljNRXaXrsybP3Ixgms8fq9HdEWZ59xFPA8y7tNnVPp4MpK8ahC7ZPkEPzfh4EZAGdEOKQqgnXg8uPEN9dp7pnv6H51Ml--Wh7CW97EdBA39xu-edATsjSxWyHSmTYkXxqiz7-0f2dv3ZOuzv9l8tdCpxcYHi0SgGYB-LTj267e9v8xNSe7-ragw__",
            "https://s3-alpha-sig.figma.com/img/28f8/2ef8/a69cbd032a9d647f1e70df95cbb3448b?Expires=1745798400&Key-Pair-Id=APKAQ4GOSFWCW27IBOMQ&Signature=Gya-3vsBGJZ-3GxIv-mNFAzFAAABeKFFHXwePxtftRCkWHd3SCT6eKJYhiu4pGM~Rvnf2dLKDdDhPS~9d8LCWFmKkM~D-G8VXJDsdRRT4RVSN6O8UBKdBCydzBKCskWf3A0V5RirebE~84aHQzIoR6KYQgNf9ZkhHChm-HGxqNPV6AASe02ALiGwDrrkv5UXjcoiX9IA5wbarsi2aFOD6EyYqqwfe7c3eITSohiqo24YX3VT~DBrzUHLEKldCcwOzMgEuLWF-eaXC6xlXtOwPP4mJYaMERg8Pei3effr2veonIJpt6WhFAb7HX5r1YgBBCXxRiM9oFCrjZ3rXEeySA__"
        )
        val nameList = listOf(
            "Ava Smith",
            "Jessie Brown",
            "Jackie Taylor",
            "Jenny Walker",
            "Freddy Harris",
            "Annie King"
        )
        val careerList =
            listOf("Photograph", "Actress", "Financier", "Make-up artist", "Secretary", "Nurse")
    }

    init{
        mutableContactList.value = getContactItems()
    }

    private fun getContactItems(): List<ContactItem> {
        val items: MutableList<ContactItem> = mutableListOf()

        for (contactId in 0..NUM_OF_USERS) {
            items.add(
                ContactItem(
                    nameList[contactId % (NUM_OF_USERS / 4)],
                    careerList[contactId % (NUM_OF_USERS / 4)],
                    URLImageList[contactId % (NUM_OF_USERS / 4)]
                )
            )
        }

        return items
    }
}