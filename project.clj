(defproject clj-flver "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/tools.cli "1.0.219"]
                 [clojure-watch "LATEST"]]
  :main ^:skip-aot clj-flver.core
  :target-path "target/%s"
  :plugins [[cider/cider-nrepl "0.24.0"]]
  :profiles {:uberjar {:aot :all}})
