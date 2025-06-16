package com.albaridbank.edition.service.excelCCP;

import com.albaridbank.edition.dto.base.CompteCCPDetailDTO;
import com.albaridbank.edition.dto.base.MouvementFinancierDTO;
import com.albaridbank.edition.dto.excelCCP.CompteMouvementVeilleExcelDTO;
import com.albaridbank.edition.dto.excelCCP.PortefeuilleClientCCPExcelDTO;
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
            createReportHeader(sheet, rapportData, titleStyle, headerStyle, normalStyle);

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
            createMouvementReportHeader(sheet, rapportData, titleStyle, headerStyle, normalStyle);

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

    private void createMouvementReportHeader(XSSFSheet sheet, CompteMouvementVeilleExcelDTO data,
                                             CellStyle titleStyle, CellStyle headerStyle, CellStyle normalStyle) {
        // Ligne 1 : Titre du rapport
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(data.getTitreRapport());
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

        // Ligne 2 : Information de l'agence
        Row agenceRow = sheet.createRow(1);
        Cell agenceCell = agenceRow.createCell(0);
        agenceCell.setCellValue("Agence: " + data.getCodeAgence() + " - " + data.getNomAgence());
        agenceCell.setCellStyle(normalStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 6));

        // Ligne 3 : Date du rapport
        Row dateRow = sheet.createRow(2);
        Cell dateCell = dateRow.createCell(0);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dateCell.setCellValue("Journée du: " + data.getJourneeDu().format(dateFormatter));
        dateCell.setCellStyle(normalStyle);
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 6));

        // Ligne 4 : Date d'édition
        Row infoRow = sheet.createRow(3);
        Cell infoCell = infoRow.createCell(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        infoCell.setCellValue("Date d'édition: " + data.getDateEdition().format(formatter));
        infoCell.setCellStyle(normalStyle);
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 6));

        // Ligne 5 : Paramètres du rapport
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
                                    CellStyle titleStyle, CellStyle headerStyle, CellStyle normalStyle) {
        // Ligne 1 : Titre du rapport
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(data.getTitreRapport());
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));

        // Ligne 2 : Information de l'agence
        Row agenceRow = sheet.createRow(1);
        Cell agenceCell = agenceRow.createCell(0);
        agenceCell.setCellValue("Agence: " + data.getCodburpo() + " - " + data.getDesburpo());
        agenceCell.setCellStyle(normalStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 9));

        // Ligne 3 : Date d'édition et utilisateur
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