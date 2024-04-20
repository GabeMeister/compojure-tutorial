(defproject compojure-tutorial "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [hiccup "2.0.0-RC3"]
                 [org.clojure/data.json "2.5.0"]
                 [com.github.seancorfield/next.jdbc "1.3.909"]
                 [org.postgresql/postgresql "42.2.24"]]
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler compojure-tutorial.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]
                        [ring/ring-codec "1.2.0"]]}})
