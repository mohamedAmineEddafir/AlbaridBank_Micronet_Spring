package com.albaridbank.edition.service.excelCCP;

import com.albaridbank.edition.dto.base.CompteCCPDetailDTO;
import com.albaridbank.edition.dto.base.MouvementFinancierDTO;
import com.albaridbank.edition.dto.base.PortefeuilleClientCCPDetailDTO;
import com.albaridbank.edition.dto.excelCCP.CompteMouvementVeilleExcelDTO;
import com.albaridbank.edition.dto.excelCCP.NbrTotalEncoursCCPExcelDTO;
import com.albaridbank.edition.dto.excelCCP.PortefeuilleClientCCPExcelDTO;
import com.albaridbank.edition.dto.excelCCP.PortefeuilleClientCCPMExcelDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExcelExportService {

    /**
     * Exporte les données du rapport "ETAT PORTEFEUILLE CLIENT CCP" au format Excel
     *
     * @param rapportData Les données du rapport à exporter
     * @return Un tableau d'octets contenant le fichier Excel
     * @throws IOException En cas d'erreur lors de la création du fichier Excel
     */
    public byte[] exportPortefeuilleClientCCPToExcel(PortefeuilleClientCCPExcelDTO rapportData) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Portefeuille Client CCP");

            // Styles pour l'en-tête
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle titleStyle = createTitleStyle(workbook);
            CellStyle normalStyle = createNormalStyle(workbook);
            CellStyle numericStyle = createNumericStyle(workbook);
            CellStyle dateStyle = createDateStyle(workbook);
            CellStyle currencyStyle = createCurrencyStyle(workbook);

            // Création de l'en-tête du rapport
            createReportHeader(sheet, rapportData, titleStyle, normalStyle);

            // Création de l'en-tête du tableau
            createTableHeader(sheet, headerStyle);

            // Remplissage des données
            fillTableData(sheet, rapportData.getComptes(), normalStyle, numericStyle, dateStyle, currencyStyle);

            // Création du pied de page (totaux)
            createFooter(sheet, rapportData, headerStyle, currencyStyle);

            // Ajuster la largeur des colonnes
            for (int i = 0; i < 10; i++) {
                sheet.autoSizeColumn(i);
            }

            // Écriture du workbook dans un ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }

    /**
     * Exporte les données du rapport "ETAT DES COMPTES MOUVEMENTES LA VEILLE" au format Excel
     *
     * @param rapportData Les données du rapport à exporter
     * @return Un tableau d'octets contenant le fichier Excel
     * @throws IOException En cas d'erreur lors de la création du fichier Excel
     */
    public byte[] exportCompteMouvementVeilleToExcel(CompteMouvementVeilleExcelDTO rapportData) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Comptes Mouvementés");

            // Styles pour l'en-tête
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle titleStyle = createTitleStyle(workbook);
            CellStyle normalStyle = createNormalStyle(workbook);
            CellStyle numericStyle = createNumericStyle(workbook);
            CellStyle currencyStyle = createCurrencyStyle(workbook);

            // Création de l'en-tête du rapport
            createMouvementReportHeader(sheet, rapportData, titleStyle, normalStyle);

            // Création de l'en-tête du tableau
            createMouvementTableHeader(sheet, headerStyle);

            // Remplissage des données
            fillMouvementTableData(sheet, rapportData.getMouvements(), normalStyle, numericStyle, currencyStyle);

            // Création du pied de page (totaux)
            createMouvementFooter(sheet, rapportData, headerStyle, currencyStyle);

            // Ajuster la largeur des colonnes
            for (int i = 0; i < 7; i++) {
                sheet.autoSizeColumn(i);
            }

            // Écriture du workbook dans un ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }

    /**
     * Exporte les données du rapport "ETAT NOMBRE TOTAL & ENCOURS GLOBAL CCP" au format Excel
     *
     * @param rapportData Les données du rapport à exporter
     * @return Un tableau d'octets contenant le fichier Excel
     * @throws IOException En cas d'erreur lors de la création du fichier Excel
     */
    public byte[] exportEncoursGlobalCCPToExcel(NbrTotalEncoursCCPExcelDTO rapportData) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Encours Global CCP");

            // Styles pour le document
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle titleStyle = createTitleStyle(workbook);
            CellStyle normalStyle = createNormalStyle(workbook);
            CellStyle currencyStyle = createCurrencyStyle(workbook);
            CellStyle numberStyle = createNumericStyle(workbook);

            // Définir la largeur des colonnes
            sheet.setColumnWidth(0, 5000);
            sheet.setColumnWidth(1, 5000);

            int rowIndex = 0;

            // Titre du rapport
            Row titleRow = sheet.createRow(rowIndex);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue(rapportData.getTitreRapport());
            titleCell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
            rowIndex++;

            // Information de l'agence
            Row agenceRow = sheet.createRow(rowIndex);
            Cell agenceLabel = agenceRow.createCell(0);
            agenceLabel.setCellValue("Bureau:");
            agenceLabel.setCellStyle(normalStyle);

            Cell agenceValue = agenceRow.createCell(1);
            agenceValue.setCellValue(rapportData.getCodeBureau() + " - " + rapportData.getDesignationBureau());
            agenceValue.setCellStyle(normalStyle);
            rowIndex++;

            // Date du rapport
            Row dateRow = sheet.createRow(rowIndex);
            Cell dateLabel = dateRow.createCell(0);
            dateLabel.setCellValue("Journée du:");
            dateLabel.setCellStyle(normalStyle);

            Cell dateValue = dateRow.createCell(1);
            dateValue.setCellValue(rapportData.getJourneeDu().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            dateValue.setCellStyle(normalStyle);
            rowIndex++;

            // Date d'édition
            Row editionRow = sheet.createRow(rowIndex);
            Cell editionLabel = editionRow.createCell(0);
            editionLabel.setCellValue("Date d'édition:");
            editionLabel.setCellStyle(normalStyle);

            Cell editionValue = editionRow.createCell(1);
            editionValue.setCellValue(rapportData.getDateEdition().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            editionValue.setCellStyle(normalStyle);
            rowIndex++;

            // Utilisateur
            Row userRow = sheet.createRow(rowIndex);
            Cell userLabel = userRow.createCell(0);
            userLabel.setCellValue("Utilisateur:");
            userLabel.setCellStyle(normalStyle);

            Cell userValue = userRow.createCell(1);
            userValue.setCellValue(rapportData.getUtilisateur());
            userValue.setCellStyle(normalStyle);
            rowIndex++;

            // Ligne vide
            sheet.createRow(rowIndex);
            rowIndex++;

            // Nombre de comptes
            Row compteRow = sheet.createRow(rowIndex);
            Cell compteLabel = compteRow.createCell(0);
            compteLabel.setCellValue("Nombre total de comptes:");
            compteLabel.setCellStyle(headerStyle);

            Cell compteValue = compteRow.createCell(1);
            compteValue.setCellValue(rapportData.getNombreComptes());
            compteValue.setCellStyle(numberStyle);
            rowIndex++;

            // Encours total
            Row encoursRow = sheet.createRow(rowIndex);
            Cell encoursLabel = encoursRow.createCell(0);
            encoursLabel.setCellValue("Encours total:");
            encoursLabel.setCellStyle(headerStyle);

            Cell encoursValue = encoursRow.createCell(1);
            encoursValue.setCellValue(rapportData.getTotalEncours().doubleValue());
            encoursValue.setCellStyle(currencyStyle);

            // Écriture du workbook dans un ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }

    private void createMouvementReportHeader(XSSFSheet sheet, CompteMouvementVeilleExcelDTO data,
                                             CellStyle titleStyle, CellStyle normalStyle) {
        //  Titre du rapport
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(data.getTitreRapport());
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

        //  Information de l'agence
        Row agenceRow = sheet.createRow(1);
        Cell agenceCell = agenceRow.createCell(0);
        agenceCell.setCellValue("Agence: " + data.getCodeAgence() + " - " + data.getNomAgence());
        agenceCell.setCellStyle(normalStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 6));

        //  Date du rapport
        Row dateRow = sheet.createRow(2);
        Cell dateCell = dateRow.createCell(0);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dateCell.setCellValue("Journée du: " + data.getJourneeDu().format(dateFormatter));
        dateCell.setCellStyle(normalStyle);
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 6));

        //  Date d'édition
        Row infoRow = sheet.createRow(3);
        Cell infoCell = infoRow.createCell(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        infoCell.setCellValue("Date d'édition: " + data.getDateEdition().format(formatter));
        infoCell.setCellStyle(normalStyle);
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 6));

        //  Paramètres du rapport
        Row paramRow = sheet.createRow(4);
        Cell paramCell = paramRow.createCell(0);
        String paramText = "Montant minimum: " + data.getMontantMinimum() + " DH";
        paramCell.setCellValue(paramText);
        paramCell.setCellStyle(normalStyle);
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 6));

        // Ligne vide
        sheet.createRow(5);
    }

    private void createMouvementTableHeader(XSSFSheet sheet, CellStyle headerStyle) {
        Row headerRow = sheet.createRow(6);

        Cell numCompteCell = headerRow.createCell(0);
        numCompteCell.setCellValue("N° Compte");
        numCompteCell.setCellStyle(headerStyle);

        Cell nomPrenomCell = headerRow.createCell(1);
        nomPrenomCell.setCellValue("Nom et Prénom");
        nomPrenomCell.setCellStyle(headerStyle);

        Cell agenceCell = headerRow.createCell(2);
        agenceCell.setCellValue("Agence");
        agenceCell.setCellStyle(headerStyle);

        Cell typeOpCell = headerRow.createCell(3);
        typeOpCell.setCellValue("Type d'opération");
        typeOpCell.setCellStyle(headerStyle);

        Cell sensCell = headerRow.createCell(4);
        sensCell.setCellValue("Sens");
        sensCell.setCellStyle(headerStyle);

        Cell montantCell = headerRow.createCell(5);
        montantCell.setCellValue("Montant");
        montantCell.setCellStyle(headerStyle);

        Cell affichageSensCell = headerRow.createCell(6);
        affichageSensCell.setCellValue("Crédit/Débit");
        affichageSensCell.setCellStyle(headerStyle);
    }

    private void fillMouvementTableData(XSSFSheet sheet, List<MouvementFinancierDTO> mouvements,
                                        CellStyle normalStyle, CellStyle numericStyle, CellStyle currencyStyle) {
        int rowNum = 7; // Commencer après l'en-tête

        for (MouvementFinancierDTO mouvement : mouvements) {
            Row row = sheet.createRow(rowNum++);

            // N° Compte
            Cell numCompteCell = row.createCell(0);
            if (mouvement.getIdencomp() != null) {
                numCompteCell.setCellValue(mouvement.getIdencomp());
                numCompteCell.setCellStyle(numericStyle);
            } else {
                numCompteCell.setCellStyle(normalStyle);
            }

            // Nom et Prénom
            Cell nomPrenomCell = row.createCell(1);
            nomPrenomCell.setCellValue(mouvement.getInticomp());
            nomPrenomCell.setCellStyle(normalStyle);

            // Agence
            Cell agenceCell = row.createCell(2);
            agenceCell.setCellValue(mouvement.getDesburpo());
            agenceCell.setCellStyle(normalStyle);

            // Type d'opération
            Cell typeOpCell = row.createCell(3);
            typeOpCell.setCellValue(mouvement.getLibtypop());
            typeOpCell.setCellStyle(normalStyle);

            // Sens
            Cell sensCell = row.createCell(4);
            sensCell.setCellValue(mouvement.getSensmouv());
            sensCell.setCellStyle(normalStyle);

            // Montant
            Cell montantCell = row.createCell(5);
            if (mouvement.getMontmouv() != null) {
                montantCell.setCellValue(mouvement.getMontmouv().doubleValue());
                montantCell.setCellStyle(currencyStyle);
            } else {
                montantCell.setCellStyle(normalStyle);
            }

            // Affichage Crédit/Débit
            Cell affichageSensCell = row.createCell(6);
            String sens = mouvement.getSensmouv();
            if ("C".equals(sens)) {
                affichageSensCell.setCellValue("Crédit");
            } else if ("D".equals(sens)) {
                affichageSensCell.setCellValue("Débit");
            } else {
                affichageSensCell.setCellValue(sens);
            }
            affichageSensCell.setCellStyle(normalStyle);
        }
    }

    private void createMouvementFooter(XSSFSheet sheet, CompteMouvementVeilleExcelDTO data,
                                       CellStyle headerStyle, CellStyle currencyStyle) {
        int lastRowNum = sheet.getLastRowNum() + 2; // Laisser une ligne vide

        // Ligne des totaux
        Row totalRow = sheet.createRow(lastRowNum);

        Cell totalLabelCell = totalRow.createCell(0);
        totalLabelCell.setCellValue("TOTAL");
        totalLabelCell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(lastRowNum, lastRowNum, 0, 4));

        Cell totalMontantCell = totalRow.createCell(5);
        totalMontantCell.setCellValue(data.getMontantTotal().doubleValue());
        totalMontantCell.setCellStyle(currencyStyle);

        // Ligne nombre de comptes
        Row compteRow = sheet.createRow(lastRowNum + 1);
        Cell compteCell = compteRow.createCell(0);
        compteCell.setCellValue("Nombre de comptes mouvementés: " + data.getNombreTotalComptes());
        compteCell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(lastRowNum + 1, lastRowNum + 1, 0, 6));
    }

    /**
     * Exporte les données du rapport "ETAT PORTEFEUILLE CLIENT M CCP" au format Excel
     * avec un design amélioré et une meilleure organisation des données
     *
     * @param rapportData Les données du rapport à exporter
     * @return Un tableau d'octets contenant le fichier Excel
     * @throws IOException En cas d'erreur lors de la création du fichier Excel
     */
    public byte[] exportPortefeuilleClientMCCPToExcel(PortefeuilleClientCCPMExcelDTO rapportData) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Portefeuille Client M CCP");

            // Styles pour le document
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle titleStyle = createTitleStyle(workbook);
            CellStyle normalStyle = createNormalStyle(workbook);
            CellStyle numericStyle = createNumericStyle(workbook);
            CellStyle dateStyle = createDateStyle(workbook);
            CellStyle currencyStyle = createCurrencyStyle(workbook);
            CellStyle highlightStyle = createHighlightStyle(workbook);
            CellStyle footerLabelStyle = createFooterLabelStyle(workbook);

            // Création de l'en-tête du rapport
            createPortefeuilleMCCPReportHeader(sheet, rapportData, titleStyle, normalStyle);

            // Création de l'en-tête du tableau
            createPortefeuilleMCCPTableHeader(sheet, headerStyle);

            // Remplissage des données
            fillPortefeuilleMCCPTableData(sheet, rapportData.getComptes(), normalStyle, numericStyle, dateStyle, currencyStyle, highlightStyle);

            // Création du pied de page (totaux)
            createPortefeuilleMCCPFooter(sheet, rapportData, headerStyle, currencyStyle, footerLabelStyle);

            // Configurer les paramètres de page
            setupSheetProperties(sheet);

            // Ajuster la largeur des colonnes
            for (int i = 0; i < 14; i++) {
                sheet.autoSizeColumn(i);
                // Ajouter un peu d'espace supplémentaire pour une meilleure lisibilité
                int width = sheet.getColumnWidth(i);
                sheet.setColumnWidth(i, width + 500);
            }

            // Écriture du workbook dans un ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }

    /**
     * Configure les propriétés de la feuille Excel
     */
    private void setupSheetProperties(XSSFSheet sheet) {
        // Configuration de l'impression
        sheet.setFitToPage(true);
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        printSetup.setFitHeight((short) 0);
        printSetup.setFitWidth((short) 1);

        // Figer les premières lignes et colonnes pour faciliter la navigation
        sheet.createFreezePane(2, 5);
    }

    /**
     * Crée un style pour mettre en évidence certaines cellules
     */
    private CellStyle createHighlightStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);

        return style;
    }

    /**
     * Crée un style pour les étiquettes de pied de page
     */
    private CellStyle createFooterLabelStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);

        return style;
    }


    private void createPortefeuilleMCCPReportHeader(XSSFSheet sheet, PortefeuilleClientCCPMExcelDTO data,
                                                    CellStyle titleStyle, CellStyle normalStyle) {
        // Logo et titre du rapport (ligne 0)
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(30); // Hauteur augmentée pour le titre

        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(data.getTitreRapport());
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 13));

        // Information de l'agence (ligne 1)
        Row agenceRow = sheet.createRow(1);
        Cell agenceLabel = agenceRow.createCell(0);
        agenceLabel.setCellValue("Bureau:");
        agenceLabel.setCellStyle(normalStyle);

        Cell agenceValue = agenceRow.createCell(1);
        agenceValue.setCellValue(data.getCodburpo() + " - " + data.getDesburpo());
        agenceValue.setCellStyle(normalStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 5));

        // Date d'édition (même ligne, à droite)
        Cell dateLabel = agenceRow.createCell(6);
        dateLabel.setCellValue("Date d'édition:");
        dateLabel.setCellStyle(normalStyle);

        Cell dateValue = agenceRow.createCell(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        dateValue.setCellValue(data.getDateEdition().format(formatter));
        dateValue.setCellStyle(normalStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 7, 9));

        // Utilisateur (même ligne, tout à droite)
        Cell userLabel = agenceRow.createCell(10);
        userLabel.setCellValue("Utilisateur:");
        userLabel.setCellStyle(normalStyle);

        Cell userValue = agenceRow.createCell(11);
        userValue.setCellValue(data.getUtilisateur());
        userValue.setCellStyle(normalStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 11, 13));

        // Récapitulatif global (ligne 2)
        Row summaryRow = sheet.createRow(2);

        Cell nbComptesLabel = summaryRow.createCell(0);
        nbComptesLabel.setCellValue("Nombre total de comptes:");
        nbComptesLabel.setCellStyle(normalStyle);

        Cell nbComptesValue = summaryRow.createCell(1);
        nbComptesValue.setCellValue(data.getNombreTotalComptes());
        nbComptesValue.setCellStyle(normalStyle);
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 3));

        Cell encoursLabel = summaryRow.createCell(4);
        encoursLabel.setCellValue("Encours total:");
        encoursLabel.setCellStyle(normalStyle);

        Cell encoursValue = summaryRow.createCell(5);
        encoursValue.setCellValue(data.getEncoursTotalComptes().doubleValue());
        encoursValue.setCellStyle(createCurrencyStyle(sheet.getWorkbook()));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 7));

        // Filtrage (ligne 3)
        Row filterRow = sheet.createRow(3);
        Cell filterLabel = filterRow.createCell(0);
        filterLabel.setCellValue("État des comptes affichés:");
        filterLabel.setCellStyle(normalStyle);

        Cell filterValue = filterRow.createCell(1);
        // Détermine l'état des comptes en examinant le premier compte
        String etatCompte = !data.getComptes().isEmpty() ? data.getComptes().getFirst().getTypeCompteLibelle() : "Tous";
        filterValue.setCellValue(etatCompte);
        filterValue.setCellStyle(normalStyle);
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 3));

        // Ligne vide
        sheet.createRow(4);
    }

    private void createPortefeuilleMCCPTableHeader(XSSFSheet sheet, CellStyle headerStyle) {
        Row headerRow = sheet.createRow(5);
        headerRow.setHeightInPoints(20); // Hauteur augmentée pour l'en-tête

        //  N° Compte
        Cell numCompteCell = headerRow.createCell(0);
        numCompteCell.setCellValue("N° Compte");
        numCompteCell.setCellStyle(headerStyle);

        //  Nom et Prénom
        Cell nomPrenomCell = headerRow.createCell(1);
        nomPrenomCell.setCellValue("Nom et Prénom");
        nomPrenomCell.setCellStyle(headerStyle);

        //  Adresse
        Cell adresseCell = headerRow.createCell(2);
        adresseCell.setCellValue("Adresse");
        adresseCell.setCellStyle(headerStyle);

        //  Code Postal
        Cell codePostalCell = headerRow.createCell(3);
        codePostalCell.setCellValue("Code Postal");
        codePostalCell.setCellStyle(headerStyle);

        //  Condensé
        Cell inticondCell = headerRow.createCell(4);
        inticondCell.setCellValue("Localité");
        inticondCell.setCellStyle(headerStyle);

        //  Cat. Soc. Prof
        Cell catSocProfCell = headerRow.createCell(5);
        catSocProfCell.setCellValue("Cat. Soc. Prof");
        catSocProfCell.setCellStyle(headerStyle);

        //  CIN
        Cell cinCell = headerRow.createCell(6);
        cinCell.setCellValue("CIN");
        cinCell.setCellStyle(headerStyle);

        //  Tél
        Cell telCell = headerRow.createCell(7);
        telCell.setCellValue("Téléphone");
        telCell.setCellStyle(headerStyle);

        // Etat Compte
        Cell etatCompteCell = headerRow.createCell(8);
        etatCompteCell.setCellValue("État Compte");
        etatCompteCell.setCellStyle(headerStyle);

        // Date Naissance
        Cell dateNaissanceCell = headerRow.createCell(9);
        dateNaissanceCell.setCellValue("Date Naissance");
        dateNaissanceCell.setCellStyle(headerStyle);

        // Solde Courant
        Cell soldeCell = headerRow.createCell(10);
        soldeCell.setCellValue("Solde Courant");
        soldeCell.setCellStyle(headerStyle);

        // Solde Opposition
        Cell soldeOppoCell = headerRow.createCell(11);
        soldeOppoCell.setCellValue("Solde Opposition");
        soldeOppoCell.setCellStyle(headerStyle);

        // Solde Certifié
        Cell soldeCertCell = headerRow.createCell(12);
        soldeCertCell.setCellValue("Solde Certifié");
        soldeCertCell.setCellStyle(headerStyle);

        // Date Solde
        Cell dateSoldeCell = headerRow.createCell(13);
        dateSoldeCell.setCellValue("Date Solde");
        dateSoldeCell.setCellStyle(headerStyle);
    }

    private void fillPortefeuilleMCCPTableData(XSSFSheet sheet, List<PortefeuilleClientCCPDetailDTO> comptes,
                                               CellStyle normalStyle, CellStyle numericStyle,
                                               CellStyle dateStyle, CellStyle currencyStyle, CellStyle highlightStyle) {
        int rowNum = 6; // Commencer après l'en-tête
        boolean alternateBg = false; // Pour créer des lignes alternées

        for (PortefeuilleClientCCPDetailDTO compte : comptes) {
            Row row = sheet.createRow(rowNum++);

            // Appliquer le style alterné pour une meilleure lisibilité
            CellStyle rowStyle = alternateBg ? highlightStyle : normalStyle;
            CellStyle rowNumericStyle = alternateBg ? highlightStyle : numericStyle;
            CellStyle rowDateStyle = alternateBg ? highlightStyle : dateStyle;
            CellStyle rowCurrencyStyle = alternateBg ? highlightStyle : currencyStyle;

            alternateBg = !alternateBg; // Inverser pour la prochaine ligne

            // N° Compte
            Cell numCompteCell = row.createCell(0);
            if (compte.getIdencomp() != null) {
                numCompteCell.setCellValue(compte.getIdencomp());
                numCompteCell.setCellStyle(rowNumericStyle);
            } else {
                numCompteCell.setCellStyle(rowStyle);
            }

            // Nom et Prénom (formater en majuscules pour une meilleure lisibilité)
            Cell nomPrenomCell = row.createCell(1);
            String nomPrenom = compte.getInticomp() != null ? compte.getInticomp().toUpperCase() : "";
            nomPrenomCell.setCellValue(nomPrenom);
            nomPrenomCell.setCellStyle(rowStyle);

            // Adresse (limiter la longueur si trop longue)
            Cell adresseCell = row.createCell(2);
            String adresse = compte.getAdrecomp();
            if (adresse != null && adresse.length() > 50) {
                adresse = adresse.substring(0, 47) + "...";
            }
            adresseCell.setCellValue(adresse);
            adresseCell.setCellStyle(rowStyle);

            // Code Postal
            Cell codePostalCell = row.createCell(3);
            codePostalCell.setCellValue(compte.getCodepost());
            codePostalCell.setCellStyle(rowStyle);

            // Condensé (Localité)
            Cell inticondCell = row.createCell(4);
            inticondCell.setCellValue(compte.getInticond());
            inticondCell.setCellStyle(rowStyle);

            // Cat. Soc. Prof
            Cell catSocProfCell = row.createCell(5);
            catSocProfCell.setCellValue(compte.getLibsocpr());
            catSocProfCell.setCellStyle(rowStyle);

            // CIN
            Cell cinCell = row.createCell(6);
            cinCell.setCellValue(compte.getNumpieid());
            cinCell.setCellStyle(rowStyle);

            // Tél (formater le numéro si possible)
            Cell telCell = row.createCell(7);
            String tel = compte.getNumetele();
            if (tel != null && tel.length() == 10 && tel.startsWith("0")) {
                tel = tel.substring(0, 4) + " " + tel.substring(4, 7) + " " + tel.substring(7);
            }
            telCell.setCellValue(tel);
            telCell.setCellStyle(rowStyle);

            // Etat Compte
            Cell etatCompteCell = row.createCell(8);
            etatCompteCell.setCellValue(compte.getTypeCompteLibelle());
            etatCompteCell.setCellStyle(rowStyle);

            // Date Naissance
            Cell dateNaissanceCell = row.createCell(9);
            if (compte.getDatenais() != null) {
                dateNaissanceCell.setCellValue(compte.getDatenais());
                dateNaissanceCell.setCellStyle(rowDateStyle);
            } else {
                dateNaissanceCell.setCellStyle(rowStyle);
            }

            // Solde Courant
            Cell soldeCell = row.createCell(10);
            if (compte.getSoldcour() != null) {
                soldeCell.setCellValue(compte.getSoldcour().doubleValue());
                soldeCell.setCellStyle(rowCurrencyStyle);
            } else {
                soldeCell.setCellStyle(rowStyle);
            }

            // Solde Opposition
            Cell soldeOppoCell = row.createCell(11);
            if (compte.getSoldoppo() != null && compte.getSoldoppo().compareTo(java.math.BigDecimal.ZERO) != 0) {
                soldeOppoCell.setCellValue(compte.getSoldoppo().doubleValue());
                soldeOppoCell.setCellStyle(rowCurrencyStyle);
            } else {
                soldeOppoCell.setCellValue(0.00);
                soldeOppoCell.setCellStyle(rowStyle);
            }

            // Solde Certifié
            Cell soldeCertCell = row.createCell(12);
            if (compte.getSoldcert() != null && compte.getSoldcert().compareTo(java.math.BigDecimal.ZERO) != 0) {
                soldeCertCell.setCellValue(compte.getSoldcert().doubleValue());
                soldeCertCell.setCellStyle(rowCurrencyStyle);
            } else {
                soldeCertCell.setCellValue(0.00);
                soldeCertCell.setCellStyle(rowStyle);
            }

            // Date Solde
            Cell dateSoldeCell = row.createCell(13);
            if (compte.getDatesold() != null) {
                dateSoldeCell.setCellValue(compte.getDatesold());
                dateSoldeCell.setCellStyle(rowDateStyle);
            } else {
                dateSoldeCell.setCellStyle(rowStyle);
            }
        }
    }

    private void createPortefeuilleMCCPFooter(XSSFSheet sheet, PortefeuilleClientCCPMExcelDTO data,
                                              CellStyle headerStyle, CellStyle currencyStyle, CellStyle footerLabelStyle) {
        int lastRowNum = sheet.getLastRowNum() + 2; // Laisser une ligne vide

        // Ligne titre du récapitulatif
        Row titleRow = sheet.createRow(lastRowNum++);
        titleRow.setHeightInPoints(20); // Hauteur augmentée

        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("RÉCAPITULATIF");
        titleCell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(lastRowNum - 1, lastRowNum - 1, 0, 13));

        // Totaux principaux première ligne
        Row totalRow1 = sheet.createRow(lastRowNum++);

        Cell nbComptesLabelCell = totalRow1.createCell(0);
        nbComptesLabelCell.setCellValue("Nombre total de comptes:");
        nbComptesLabelCell.setCellStyle(footerLabelStyle);
        sheet.addMergedRegion(new CellRangeAddress(lastRowNum - 1, lastRowNum - 1, 0, 2));

        Cell nbComptesValueCell = totalRow1.createCell(3);
        nbComptesValueCell.setCellValue(data.getNombreTotalComptes());
        nbComptesValueCell.setCellStyle(headerStyle);

        Cell encoursLabelCell = totalRow1.createCell(5);
        encoursLabelCell.setCellValue("Encours total:");
        encoursLabelCell.setCellStyle(footerLabelStyle);
        sheet.addMergedRegion(new CellRangeAddress(lastRowNum - 1, lastRowNum - 1, 5, 7));

        Cell encoursValueCell = totalRow1.createCell(8);
        encoursValueCell.setCellValue(data.getEncoursTotalComptes().doubleValue());
        encoursValueCell.setCellStyle(currencyStyle);
        sheet.addMergedRegion(new CellRangeAddress(lastRowNum - 1, lastRowNum - 1, 8, 9));

        // Deuxième ligne de totaux
        Row totalRow2 = sheet.createRow(lastRowNum++);

        Cell oppoLabelCell = totalRow2.createCell(0);
        oppoLabelCell.setCellValue("Solde opposition total:");
        oppoLabelCell.setCellStyle(footerLabelStyle);
        sheet.addMergedRegion(new CellRangeAddress(lastRowNum - 1, lastRowNum - 1, 0, 2));

        Cell oppoValueCell = totalRow2.createCell(3);
        oppoValueCell.setCellValue(data.getTotalSoldeOpposition().doubleValue());
        oppoValueCell.setCellStyle(currencyStyle);

        Cell certLabelCell = totalRow2.createCell(5);
        certLabelCell.setCellValue("Solde certifié total:");
        certLabelCell.setCellStyle(footerLabelStyle);
        sheet.addMergedRegion(new CellRangeAddress(lastRowNum - 1, lastRowNum - 1, 5, 7));

        Cell certValueCell = totalRow2.createCell(8);
        certValueCell.setCellValue(data.getTotalSoldeCertifie().doubleValue());
        certValueCell.setCellStyle(currencyStyle);
        sheet.addMergedRegion(new CellRangeAddress(lastRowNum - 1, lastRowNum - 1, 8, 9));

        // Troisième ligne de totaux
        Row totalRow3 = sheet.createRow(lastRowNum++);

        Cell taxeLabelCell = totalRow3.createCell(0);
        taxeLabelCell.setCellValue("Solde taxe total:");
        taxeLabelCell.setCellStyle(footerLabelStyle);
        sheet.addMergedRegion(new CellRangeAddress(lastRowNum - 1, lastRowNum - 1, 0, 2));

        Cell taxeValueCell = totalRow3.createCell(3);
        taxeValueCell.setCellValue(data.getTotalSoldeTaxe().doubleValue());
        taxeValueCell.setCellStyle(currencyStyle);

        Cell debitLabelCell = totalRow3.createCell(5);
        debitLabelCell.setCellValue("Solde débit opérations:");
        debitLabelCell.setCellStyle(footerLabelStyle);
        sheet.addMergedRegion(new CellRangeAddress(lastRowNum - 1, lastRowNum - 1, 5, 7));

        Cell debitValueCell = totalRow3.createCell(8);
        debitValueCell.setCellValue(data.getTotalSoldeDebitOperations().doubleValue());
        debitValueCell.setCellStyle(currencyStyle);
        sheet.addMergedRegion(new CellRangeAddress(lastRowNum - 1, lastRowNum - 1, 8, 9));

        // Quatrième ligne de totaux
        Row totalRow4 = sheet.createRow(lastRowNum++);

        Cell creditLabelCell = totalRow4.createCell(0);
        creditLabelCell.setCellValue("Solde crédit opérations:");
        creditLabelCell.setCellStyle(footerLabelStyle);
        sheet.addMergedRegion(new CellRangeAddress(lastRowNum - 1, lastRowNum - 1, 0, 2));

        Cell creditValueCell = totalRow4.createCell(3);
        creditValueCell.setCellValue(data.getTotalSoldeCreditOperations().doubleValue());
        creditValueCell.setCellStyle(currencyStyle);

        Cell periodeLabelCell = totalRow4.createCell(5);
        periodeLabelCell.setCellValue("Solde opérations période:");
        periodeLabelCell.setCellStyle(footerLabelStyle);
        sheet.addMergedRegion(new CellRangeAddress(lastRowNum - 1, lastRowNum - 1, 5, 7));

        Cell periodeValueCell = totalRow4.createCell(8);
        periodeValueCell.setCellValue(data.getTotalSoldeOperationsPeriode().doubleValue());
        periodeValueCell.setCellStyle(currencyStyle);
        sheet.addMergedRegion(new CellRangeAddress(lastRowNum - 1, lastRowNum - 1, 8, 9));

        // Note de bas de page
        Row noteRow = sheet.createRow(lastRowNum + 1);
        Cell noteCell = noteRow.createCell(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        noteCell.setCellValue("Rapport généré le " + data.getDateEdition().format(formatter) +
                " par " + data.getUtilisateur() + " | État du rapport: FINAL");
        CellStyle noteStyle = sheet.getWorkbook().createCellStyle();
        noteStyle.setAlignment(HorizontalAlignment.CENTER);
        Font italicFont = sheet.getWorkbook().createFont();
        italicFont.setItalic(true);
        noteStyle.setFont(italicFont);
        noteCell.setCellStyle(noteStyle);
        sheet.addMergedRegion(new CellRangeAddress(lastRowNum + 1, lastRowNum + 1, 0, 13));
    }

    private CellStyle createHeaderStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(12);
        style.setFont(font);

        return style;
    }

    private CellStyle createTitleStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        style.setFont(font);

        return style;
    }

    private CellStyle createNormalStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }

    private CellStyle createNumericStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setDataFormat(workbook.createDataFormat().getFormat("0"));

        return style;
    }

    private CellStyle createDateStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setDataFormat(workbook.createDataFormat().getFormat("dd/mm/yyyy"));

        return style;
    }

    private CellStyle createCurrencyStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setDataFormat(workbook.createDataFormat().getFormat("#,##0.00 DH"));

        return style;
    }

    private void createReportHeader(XSSFSheet sheet, PortefeuilleClientCCPExcelDTO data,
                                    CellStyle titleStyle, CellStyle normalStyle) {
        //  Titre du rapport
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(data.getTitreRapport());
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));

        //  Information de l'agence
        Row agenceRow = sheet.createRow(1);
        Cell agenceCell = agenceRow.createCell(0);
        agenceCell.setCellValue("Agence: " + data.getCodburpo() + " - " + data.getDesburpo());
        agenceCell.setCellStyle(normalStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 9));

        //  Date d'édition et utilisateur
        Row infoRow = sheet.createRow(2);
        Cell dateCell = infoRow.createCell(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        dateCell.setCellValue("Date d'édition: " + data.getDateEdition().format(formatter) +
                " | Utilisateur: " + data.getUtilisateur());
        dateCell.setCellStyle(normalStyle);
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 9));

        // Ligne vide
        sheet.createRow(3);
    }

    private void createTableHeader(XSSFSheet sheet, CellStyle headerStyle) {
        Row headerRow = sheet.createRow(4);

        Cell numCompteCell = headerRow.createCell(0);
        numCompteCell.setCellValue("N° Compte");
        numCompteCell.setCellStyle(headerStyle);

        Cell nomPrenomCell = headerRow.createCell(1);
        nomPrenomCell.setCellValue("Nom et Prénom");
        nomPrenomCell.setCellStyle(headerStyle);

        Cell adresseCell = headerRow.createCell(2);
        adresseCell.setCellValue("Adresse");
        adresseCell.setCellStyle(headerStyle);

        Cell catSocProfCell = headerRow.createCell(3);
        catSocProfCell.setCellValue("Cat. Soc. Prof");
        catSocProfCell.setCellStyle(headerStyle);

        Cell cinCell = headerRow.createCell(4);
        cinCell.setCellValue("CIN");
        cinCell.setCellStyle(headerStyle);

        Cell telCell = headerRow.createCell(5);
        telCell.setCellValue("Tél");
        telCell.setCellStyle(headerStyle);

        Cell etatCompteCell = headerRow.createCell(6);
        etatCompteCell.setCellValue("Etat Compte");
        etatCompteCell.setCellStyle(headerStyle);

        Cell dateNaissanceCell = headerRow.createCell(7);
        dateNaissanceCell.setCellValue("Date Naissance");
        dateNaissanceCell.setCellStyle(headerStyle);

        Cell soldeCell = headerRow.createCell(8);
        soldeCell.setCellValue("Solde");
        soldeCell.setCellStyle(headerStyle);

        Cell typeCompteCell = headerRow.createCell(9);
        typeCompteCell.setCellValue("Type Compte");
        typeCompteCell.setCellStyle(headerStyle);
    }

    private void fillTableData(XSSFSheet sheet, List<CompteCCPDetailDTO> comptes,
                               CellStyle normalStyle, CellStyle numericStyle,
                               CellStyle dateStyle, CellStyle currencyStyle) {
        int rowNum = 5; // Commencer après l'en-tête

        for (CompteCCPDetailDTO compte : comptes) {
            Row row = sheet.createRow(rowNum++);

            // N° Compte
            Cell numCompteCell = row.createCell(0);
            if (compte.getIdencomp() != null) {
                numCompteCell.setCellValue(compte.getIdencomp());
                numCompteCell.setCellStyle(numericStyle);
            } else {
                numCompteCell.setCellStyle(normalStyle);
            }

            // Nom et Prénom
            Cell nomPrenomCell = row.createCell(1);
            nomPrenomCell.setCellValue(compte.getInticomp());
            nomPrenomCell.setCellStyle(normalStyle);

            // Adresse
            Cell adresseCell = row.createCell(2);
            adresseCell.setCellValue(compte.getAdrecomp());
            adresseCell.setCellStyle(normalStyle);

            // Cat. Soc. Prof
            Cell catSocProfCell = row.createCell(3);
            catSocProfCell.setCellValue(compte.getLibsocpr());
            catSocProfCell.setCellStyle(normalStyle);

            // CIN
            Cell cinCell = row.createCell(4);
            cinCell.setCellValue(compte.getNumpieid());
            cinCell.setCellStyle(normalStyle);

            // Tél
            Cell telCell = row.createCell(5);
            telCell.setCellValue(compte.getNumetele());
            telCell.setCellStyle(normalStyle);

            // Etat Compte
            Cell etatCompteCell = row.createCell(6);
            etatCompteCell.setCellValue(compte.getEtatCompte());
            etatCompteCell.setCellStyle(normalStyle);

            // Date Naissance
            Cell dateNaissanceCell = row.createCell(7);
            if (compte.getDatenais() != null) {
                dateNaissanceCell.setCellValue(compte.getDatenais());
                dateNaissanceCell.setCellStyle(dateStyle);
            } else {
                dateNaissanceCell.setCellStyle(normalStyle);
            }

            // Solde
            Cell soldeCell = row.createCell(8);
            if (compte.getSoldcour() != null) {
                soldeCell.setCellValue(compte.getSoldcour().doubleValue());
                soldeCell.setCellStyle(currencyStyle);
            } else {
                soldeCell.setCellStyle(normalStyle);
            }

            // Type Compte
            Cell typeCompteCell = row.createCell(9);
            String typeCompte = compte.getTypeCompteLibelle();
            if (typeCompte == null && compte.getComptetyp() != null) {
                typeCompte = compte.getComptetyp().toString();
            }
            typeCompteCell.setCellValue(typeCompte);
            typeCompteCell.setCellStyle(normalStyle);
        }
    }

    private void createFooter(XSSFSheet sheet, PortefeuilleClientCCPExcelDTO data,
                              CellStyle headerStyle, CellStyle currencyStyle) {
        int lastRowNum = sheet.getLastRowNum() + 2; // Laisser une ligne vide

        // Ligne des totaux
        Row totalRow = sheet.createRow(lastRowNum);

        Cell totalLabelCell = totalRow.createCell(0);
        totalLabelCell.setCellValue("TOTAL");
        totalLabelCell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(lastRowNum, lastRowNum, 0, 7));

        Cell totalCountCell = totalRow.createCell(8);
        totalCountCell.setCellValue("Nombre de comptes: " + data.getNombreTotalComptes());
        totalCountCell.setCellStyle(headerStyle);

        Cell totalSoldeCell = totalRow.createCell(9);
        totalSoldeCell.setCellValue(data.getEncoursTotalComptes().doubleValue());
        totalSoldeCell.setCellStyle(currencyStyle);
    }
}