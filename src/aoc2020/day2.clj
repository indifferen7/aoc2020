(ns aoc2020.day2
  (:require [aoc2020.utils :as utils]
            [clojure.string :as string]))

(def input
  (->> "day2"
       (utils/lines-from)
       (map #(string/split % #"\s+"))))

(defn parse-range [input]
  (let [[from to] (string/split input #"-")]
    [(Integer/parseInt from) (Integer/parseInt to)]))

(defn parse-line [line]
  {:range (parse-range (nth line 0))
   :char (first (nth line 1))
   :password (nth line 2)})

(defn is-valid-part-one? [{:keys [char password]
                           [from to] :range}]
  (let [freq (frequencies password)
        char-freq (get freq char)]
    (and
      (not (nil? char-freq))
      (<= from char-freq to))))

(def first-part
  (->> input
       (map parse-line)
       (map is-valid-part-one?)
       (filter true?)
       (count)))

(println "Solution first part: " first-part)

(defn is-valid-part-two? [{:keys [char password]
                           [from to] :range}]
  (let [first-char (nth password (dec from))
        second-char (nth password (dec to))]
    (-> (filter #{char} [first-char second-char])
        (count)
        (= 1))))

(def second-part
  (->> input
       (map parse-line)
       (map is-valid-part-two?)
       (filter true?)
       (count)))

(println "Solution second part: " second-part)