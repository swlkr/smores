(ns smores.utils)

(defn flip [f]
  (fn [x y] (f y x)))

