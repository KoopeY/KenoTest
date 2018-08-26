package ru.koopey.test_keno.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.koopey.test_keno.utils.FileUtils;

import java.io.IOException;
import java.util.List;

@Component
public class WinCoeff {
    private static Logger logger = LoggerFactory.getLogger(WinCoeff.class);

    private boolean isReady = false;
    private int[][] coeff;

    @Value("${game.coeff.path:win_matrix.txt}")
    private String filePath;

    private WinCoeff() {
        readCoeff();
    }

    public int getCoeff(int ballsCount, int matchedCount) {
        return coeff[ballsCount - 1][matchedCount];
    }

    private void readCoeff() {
        try {
            List<String> lines = FileUtils.readFileLines("win_matrix.txt");
            int rows = lines.size();
            if (rows > 0) {
                int columns = lines.get(0).split(" ").length;
                coeff = new int[columns][rows];
                for (int i = 0; i < lines.size(); i++) {
                    String[] elements = lines.get(i).split(" ");
                    for (int j = 0; j < elements.length; j++) {
                        coeff[j][i] = Integer.parseInt(elements[j].trim());
                    }
                }
                isReady = true;
            }
        } catch (IOException e) {
            logger.error("Init coeff matrix", e);
        }
    }
}
