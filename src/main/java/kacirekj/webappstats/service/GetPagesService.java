package kacirekj.webappstats.service;

import kacirekj.webappstats.data.Keyword;
import kacirekj.webappstats.repository.KeywordRepo;
import kacirekj.webappstats.data.KeywordTimeline;
import kacirekj.webappstats.repository.KeywordTimelineRepo;
import org.apache.commons.jcs.utils.zip.CompressionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GetPagesService {

    @Autowired
    KeywordRepo keywordRepo;
    @Autowired
    KeywordTimelineRepo keywordTimelineRepo;
    @Autowired
    TimeService timeService;

    public KeywordTimeline processKeyword(final Keyword keyword, final long time) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.79 Safari/537.36");
        headers.set("Accept", "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headers.set("Accept-encoding", "gzip, deflate, br");
        headers.set("Accept-language", "cs,en;q=0.9,en-GB;q=0.8");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        byte[] r = restTemplate.exchange(keyword.getUrl(), HttpMethod.GET, entity, byte[].class).getBody();

        String decompressed = null;
        try {
            decompressed = new String(CompressionUtil.decompressGzipByteArray(r), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Matcher m = Pattern.compile(keyword.getPattern()).matcher(decompressed);
        int count = -1;
        while(m.find()) {
            count = stringToInt(m.group());
        }

        return new KeywordTimeline(
                null,
                time,
                keyword,
                count,
                "");
    }

    public int stringToInt(String s) {
        String r = s.replaceAll("\\D", "");
        return Integer.parseInt(r);
    }
}
