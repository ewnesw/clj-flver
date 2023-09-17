(ns clj-flver.core
  (:require [clojure-watch.core :refer [start-watch]]))

(def paths (atom {}))

(defn watch-path
  "take a path and start watching the given path"
  [path]
  (start-watch [{:path path
               :event-types [:create :modify :delete]
               :bootstrap (fn [path] (println "Starting to watch " path))
               :callback (fn [event filename] (println "proc" event filename))
               :options {:recursive true}}]))

(defn add-path
  "add given path and return fn from watch-path to atom paths"
  [path]
  (swap! paths
         (fn [current-state]
           (assoc current-state path (watch-path path)))))

(defn it-path
  "iterate and apply add-path"
  [lpaths]
  (map add-path lpaths))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (dorun (map println (it-path args))))
