/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kacirekj.webappstats.data;

import kacirekj.webappstats.repository.KeywordRepo;
import kacirekj.webappstats.repository.KeywordTimelineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class DatabaseLoaderService {

	private final KeywordRepo keywordRepo;
    KeywordTimelineRepo keywordTimelineRepo;

	private final String PATTERN_INDEED = "(?<=meta name=\"description\" content=\")(([0-9]{1,3}.[0-9]{1,3})|([0-9]{1,3} [^0-9]))";

	@Autowired
	public DatabaseLoaderService(KeywordRepo keywordRepo, KeywordTimelineRepo keywordTimelineRepo) {
		this.keywordRepo = keywordRepo;
		this.keywordTimelineRepo = keywordTimelineRepo;
	}

	public void loadData() throws Exception {
	    // init Keywords
        Keyword init = newKeyw("java",          "https://cz.indeed.com/jobs?q=java&l=");
        newKeyw("java spring",   "https://cz.indeed.com/jobs?q=java+spring&l=");
        newKeyw(".net",          "https://cz.indeed.com/jobs?q=.net&l=");
        newKeyw("python",        "https://cz.indeed.com/jobs?q=python&l=");
        newKeyw("c++",           "https://cz.indeed.com/c++-jobs" );
        newKeyw("javascript",    "https://cz.indeed.com/jobs?q=javascript&l=");
        newKeyw("hibernate",     "https://cz.indeed.com/jobs?q=hibernate&l=" );
        newKeyw("mybatis",       "https://cz.indeed.com/jobs?q=mybatis&l=" );
        newKeyw("java swing",    "https://cz.indeed.com/jobs?q=java+swing&l=" );
        newKeyw("javafx",        "https://cz.indeed.com/jobs?q=javafx&l=" );
        newKeyw("javascript angular",    "https://cz.indeed.com/jobs?q=javascript+angular&l=");
        newKeyw("cobol",         "https://cz.indeed.com/jobs?q=cobol&l=");
        newKeyw("javascript react","https://cz.indeed.com/jobs?q=javascript+react&l=");
        newKeyw("sql",           "https://cz.indeed.com/jobs?q=sql&l=");
        newKeyw("linux",         "https://cz.indeed.com/jobs?q=linux&l=");
        newKeyw("perl",          "https://cz.indeed.com/jobs?q=perl&l=");
        newKeyw("intellij",      "https://cz.indeed.com/jobs?q=intellij&l=");
        newKeyw("java jsf",      "https://cz.indeed.com/jobs?q=java+jsf&l=");
        newKeyw("java ee",       "https://cz.indeed.com/jobs?q=java+ee&l=");
        newKeyw("php",           "https://cz.indeed.com/jobs?q=php&l=");
        newKeyw("windows",           "https://cz.indeed.com/jobs?q=windows&l=");
        newKeyw("fortran",           "https://cz.indeed.com/jobs?q=fortran&l=");
        newKeyw("kotlin",           "https://cz.indeed.com/jobs?q=kotlin&l=");
        newKeyw("java scala",           "https://cz.indeed.com/jobs?q=java+scala&l=");
        newKeyw("asp.net",           "https://cz.indeed.com/jobs?q=asp.net&l=");
        newKeyw(".net mvc",           "https://cz.indeed.com/jobs?q=.net+mvc&l=");
        newKeyw("intellij idea",           "https://cz.indeed.com/jobs?q=intellij+idea&l=");
        newKeyw("eclipse",           "https://cz.indeed.com/jobs?q=eclipse&l=");
        newKeyw("netbeans",           "https://cz.indeed.com/jobs?q=netbeans&l=");
        newKeyw("node.js",           "https://cz.indeed.com/jobs?q=node.js&l=");
        newKeyw("websphere",           "https://cz.indeed.com/jobs?q=websphere&l=");
        newKeyw("jrebel",           "https://cz.indeed.com/jobs?q=jrebel&l=");
        newKeyw("wildfly",           "https://cz.indeed.com/jobs?q=wildfly&l=");
        newKeyw("weblogic",           "https://cz.indeed.com/jobs?q=weblogic&l=");

        newKeyw("tomcat",           "https://cz.indeed.com/jobs?q=tomcat&l=");
        newKeyw("python django",           "https://cz.indeed.com/jobs?q=python+django&l=");
        newKeyw("javascript typescript",           "https://cz.indeed.com/jobs?q=javascript+typescript&l=");
        newKeyw("javascript dart",           "https://cz.indeed.com/jobs?q=javascript+dart&l=");
        newKeyw("golang",           "https://cz.indeed.com/jobs?q=golang&l=");
        newKeyw("haskel",           "https://cz.indeed.com/jobs?q=haskel&l=");
        newKeyw("google",           "https://cz.indeed.com/jobs?q=google&l=");
        newKeyw("microsoft",           "https://cz.indeed.com/jobs?q=microsoft&l=");
        newKeyw("oracle",           "https://cz.indeed.com/jobs?q=oracle&l=");
        newKeyw("swift",           "https://cz.indeed.com/jobs?q=swift&l=");
        newKeyw("objectivec",           "https://cz.indeed.com/jobs?q=objectivec&l=");
        newKeyw(".net core",           "https://cz.indeed.com/jobs?q=.net+core&l=");

        // init KeywordsTimeline
        KeywordTimeline initT = new KeywordTimeline(0, 0L, init, 0, "");
        keywordTimelineRepo.save(initT);
	}

	private Keyword newKeyw(String name, String url) {
	    Keyword k = new Keyword(url, name, PATTERN_INDEED, "");
        keywordRepo.save(k);
        return k;
    }
}
