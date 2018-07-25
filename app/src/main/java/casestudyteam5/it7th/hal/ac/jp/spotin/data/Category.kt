package casestudyteam5.it7th.hal.ac.jp.spotin.data

import casestudyteam5.it7th.hal.ac.jp.spotin.R

enum class Category(val cateName: String, val nameForText: String, val image: Int) {
  AMUSEMENT_PARK("amusement_park", "amusement\npark", R.drawable.amusement_park),
  CAFE("cafe", "cafe", R.drawable.cafe),
  DEPARTMENT("department", "department", R.drawable.department),
  FOOD("food", "food", R.drawable.food),
  GYM("gym", "gym", R.drawable.gym),
  MALL("mall", "mall", R.drawable.mall),
  MUSEUM("museum", "museum", R.drawable.museum),
  POLICE("police", "police", R.drawable.police),
  RESTAURANT("restaurant", "restaurant", R.drawable.restaurant),
  SHOP("shop", "shop", R.drawable.shop),
  SPA("spa", "spa", R.drawable.spa),
  ZOO("zoo", "zoo", R.drawable.zoo),
}
