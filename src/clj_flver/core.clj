(ns clj-flver.core
  (:require [clojure-watch.core :refer [start-watch]]))

(defn add-file
  "take a path and start watching the given path"
  [path]
  ((start-watch [{:path path
               :event-types [:create :modify :delete]
               :bootstrap (fn [path] (println "Starting to watch " path))
               :callback (fn [event filename] (println event filename))
               :options {:recursive true}}])))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (add-file (first args)))

;;(main "/home/laiminux/.bash_aliases")
