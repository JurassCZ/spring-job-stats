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
