package com.atixlabs.semillasmiddleware.app.didi.service;

import com.atixlabs.semillasmiddleware.app.didi.constant.DidiSyncStatus;
import com.atixlabs.semillasmiddleware.app.didi.model.DidiAppUser;
import com.atixlabs.semillasmiddleware.app.exceptions.CredentialException;
import com.atixlabs.semillasmiddleware.app.model.credential.*;
import com.atixlabs.semillasmiddleware.app.model.credential.constants.CredentialCategoriesCodes;
import com.atixlabs.semillasmiddleware.app.model.credentialState.constants.RevocationReasonsCodes;
import com.atixlabs.semillasmiddleware.app.sancor.model.SancorPolicy;
import com.atixlabs.semillasmiddleware.app.sancor.service.SancorPolicyService;
import com.atixlabs.semillasmiddleware.app.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class SyncDidiProcessService {

    private CredentialCreditService credentialCreditService;

    private CredentialBenefitService credentialBenefitService;

    private CredentialIdentityService credentialIdentityService;

    private CredentialDwellingService credentialDwellingService;

    private CredentialEntrepreneurshipService credentialEntrepreneurshipService;

    private CredentialBenefitSancorService credentialBenefitSancorService;

    private DidiAppUserService didiAppUserService;

    private DidiService didiService;

    private SancorPolicyService sancorPolicyService;

    @Autowired
    public SyncDidiProcessService(CredentialCreditService credentialCreditService, DidiAppUserService didiAppUserService, DidiService didiService, CredentialBenefitService credentialBenefitService, CredentialIdentityService credentialIdentityService, CredentialDwellingService credentialDwellingService, CredentialEntrepreneurshipService credentialEntrepreneurshipService, CredentialBenefitSancorService credentialBenefitSancorService, SancorPolicyService sancorPolicyService){
        this.credentialCreditService = credentialCreditService;
        this.didiAppUserService = didiAppUserService;
        this.didiService = didiService;
        this.credentialBenefitService = credentialBenefitService;
        this.credentialIdentityService = credentialIdentityService;
        this.credentialDwellingService = credentialDwellingService;
        this.credentialEntrepreneurshipService = credentialEntrepreneurshipService;
        this.credentialBenefitSancorService = credentialBenefitSancorService;
        this.sancorPolicyService = sancorPolicyService;
    }


    public Map<CredentialCategoriesCodes, String> emmitAllCredentialsOnPendindDidiState(){

        Map<CredentialCategoriesCodes, String> response = new HashMap<CredentialCategoriesCodes, String>();

        try {
            this.emmitCredentialsIdentity();
        } catch (CredentialException e) {
            log.error("Error emmiting Identity credentials : {} ",e.getMessage(), e);
            response.put(CredentialCategoriesCodes.IDENTITY,e.getMessage());
        }

        try {
            this.emmitCredentialsDwelling();
        } catch (CredentialException e) {
            log.error("Error emmiting Dwelling credentials : {} ",e.getMessage(), e);
            response.put(CredentialCategoriesCodes.DWELLING,e.getMessage());
        }

        try {
            this.emmitCredentialsEntrepreneurship();
        } catch (CredentialException e) {
            log.error("Error emmiting Entrepreneurship credentials : {} ",e.getMessage(), e);
            response.put(CredentialCategoriesCodes.ENTREPRENEURSHIP,e.getMessage());
        }

        try {
            this.emmitCredentialsCredit();
        } catch (CredentialException e) {
            log.error("Error emmiting Credit credentials : {} ",e.getMessage(), e);
            response.put(CredentialCategoriesCodes.CREDIT,e.getMessage());
        }

        try {
            this.emmitCredentialsBenefit();
        } catch (CredentialException e) {
            log.error("Error emmiting Benefit credentials : {} ",e.getMessage(), e);
            response.put(CredentialCategoriesCodes.BENEFIT,e.getMessage());
        }

        try {
            this.emmitCredentialsBenefitSancor();
        } catch (CredentialException e) {
            log.error("Error emmiting Benefit Sancor credentials : {} ",e.getMessage(), e);
            response.put(CredentialCategoriesCodes.BENEFIT_SANCOR,e.getMessage());
        }

        return response;
    }

    public void emmitCredentialsCredit() throws CredentialException {

        List<CredentialCredit> credentialCreditsToEmmit = this.credentialCreditService.getCredentialCreditsOnPendindDidiState();

        if(credentialCreditsToEmmit==null || credentialCreditsToEmmit.isEmpty()){
            log.info("No credit credentials to emmit were found");
        }else{

            log.info(" {} Credential Credits to emmit", credentialCreditsToEmmit.size());

            for(CredentialCredit credentialCredit : credentialCreditsToEmmit){
                this.emmitCredentialCredit(credentialCredit);
            }

        }
    }

    public void emmitCredentialsBenefit() throws CredentialException {

        List<CredentialBenefits> credentialBenefitsToEmmit = this.credentialBenefitService.getCredentialBenefitsOnPendindDidiState();

        if(credentialBenefitsToEmmit==null || credentialBenefitsToEmmit.isEmpty()){
            log.info("No benefits credentials to emmit were found");
        }else{

            log.info(" {} Credential Benefits to emmit", credentialBenefitsToEmmit.size());

            for(CredentialBenefits credentialBenefits : credentialBenefitsToEmmit){
                this.emmitCredentialBenefit(credentialBenefits);
            }

        }
    }


    public void emmitCredentialsIdentity() throws CredentialException {

        List<CredentialIdentity> credentialsIdentityToEmmit = this.credentialIdentityService.getCredentialIdentityOnPendindDidiState();

        if(credentialsIdentityToEmmit==null || credentialsIdentityToEmmit.isEmpty()){
            log.info("No Identity credentials to emmit were found");
        }else{

            log.info(" {} Credential Identity to emmit", credentialsIdentityToEmmit.size());

            for(CredentialIdentity credentialIdentity : credentialsIdentityToEmmit){
                this.emmitCredentialIdentity(credentialIdentity);
            }

        }
    }

    public void emmitCredentialsDwelling() throws CredentialException {

        List<CredentialDwelling> credentialDwellingToEmmit = this.credentialDwellingService.getCredentialDwellingOnPendindDidiState();

        if(credentialDwellingToEmmit==null || credentialDwellingToEmmit.isEmpty()){
            log.info("No Dwelling credentials to emmit were found");
        }else{

            log.info(" {} Credential Dwellingtity to emmit", credentialDwellingToEmmit.size());

            for(CredentialDwelling credentialDwelling : credentialDwellingToEmmit){
                this.emmitCredentialDwelling(credentialDwelling);
            }

        }
    }

    public void emmitCredentialsEntrepreneurship() throws CredentialException {

        List<CredentialEntrepreneurship> credentialsEntrepreneurshipToEmmit = this.credentialEntrepreneurshipService.getCredentialEntrepreneurshipOnPendindDidiState();

        if(credentialsEntrepreneurshipToEmmit==null || credentialsEntrepreneurshipToEmmit.isEmpty()){
            log.info("No Entrepreneurship credentials to emmit were found");
        }else{

            log.info(" {} Credential Entrepreneurship to emmit", credentialsEntrepreneurshipToEmmit.size());

            for(CredentialEntrepreneurship credentialEntrepreneurship : credentialsEntrepreneurshipToEmmit){
                this.emmitCredentialEntrepreneurship(credentialEntrepreneurship);
            }

        }
    }

    public void emmitCredentialsBenefitSancor() throws CredentialException {

        List<CredentialBenefitSancor> credentialsBenefitSancorToEmmit = this.credentialBenefitSancorService.getCredentialBenefitSancorsOnPendindDidiState();

        if(credentialsBenefitSancorToEmmit==null || credentialsBenefitSancorToEmmit.isEmpty()){
            log.info("No Benefit Sancor credentials to emmit were found");
        }else{

            log.info(" {} Benefit Sancor to emmit", credentialsBenefitSancorToEmmit.size());

            for(CredentialBenefitSancor credentialBenefitSancorToEmmit : credentialsBenefitSancorToEmmit){
                this.emmitCredentialBenefitSancor(credentialBenefitSancorToEmmit);
            }

        }
    }


    /**
     * get current Did for holder and emmit credential
     * @param credentialCredit
     */
    public void emmitCredentialCredit(CredentialCredit credentialCredit){

        log.info("Emmiting Credential Credit id {} idBondarea {} holder {}",credentialCredit.getId(), credentialCredit.getIdBondareaCredit(), credentialCredit.getCreditHolderDni());

        Optional<DidiAppUser> didiAppUser = this.didiAppUserService.getDidiAppUserByDni(credentialCredit.getCreditHolderDni());

        if(didiAppUser.isPresent()) {
            credentialCredit.setIdDidiReceptor(didiAppUser.get().getDid());
            credentialCredit = credentialCreditService.save(credentialCredit);

            didiService.createAndEmmitCertificateDidi(credentialCredit);

        }else{
            log.info("Id Didi for Holder {} not exist, Credential Credit for loan {} not emmited", credentialCredit.getCreditHolderDni(), credentialCredit.getIdBondareaCredit());
        }

    }

    public void emmitCredentialBenefit(CredentialBenefits credentialBenefit){

        log.info("Emmiting Credential Benefit id {} holder {} beneficiary {}",credentialBenefit.getId(), credentialBenefit.getCreditHolderDni(), credentialBenefit.getBeneficiaryDni());

        Optional<DidiAppUser> didiAppUser = this.didiAppUserService.getDidiAppUserByDni(credentialBenefit.getBeneficiaryDni());

        if(didiAppUser.isPresent()) {
            credentialBenefit.setIdDidiReceptor(didiAppUser.get().getDid());
            credentialBenefit = credentialBenefitService.save(credentialBenefit);

            didiService.createAndEmmitCertificateDidi(credentialBenefit);

        }else{
            log.info("Id Didi for Benefociary {} not exist, Credential Benefit {} not emmited", credentialBenefit.getCreditHolderDni(), credentialBenefit.getId());
        }

    }

    public void emmitCredentialIdentity(CredentialIdentity credentialIdentity){

        log.info("Emmiting Credential identity id {} holder {} beneficiary {}",credentialIdentity.getId(), credentialIdentity.getCreditHolderDni(), credentialIdentity.getBeneficiaryDni());

        Optional<DidiAppUser> didiAppUser = this.didiAppUserService.getDidiAppUserByDni(credentialIdentity.getBeneficiaryDni());

        if(didiAppUser.isPresent()) {
            credentialIdentity.setIdDidiReceptor(didiAppUser.get().getDid());
            credentialIdentity = credentialIdentityService.save(credentialIdentity);

            didiService.createAndEmmitCertificateDidi(credentialIdentity);

        }else{
            log.info("Id Didi for Beneficiary {} not exist, Credential Identity {} not emmited", credentialIdentity.getCreditHolderDni(), credentialIdentity.getId());
        }

    }

    public void emmitCredentialDwelling(CredentialDwelling credentialDwelling){

        log.info("Emmiting Credential Dwelling id {} holder {} beneficiary {}",credentialDwelling.getId(), credentialDwelling.getCreditHolderDni(), credentialDwelling.getBeneficiaryDni());

        Optional<DidiAppUser> didiAppUser  = this.didiAppUserService.getDidiAppUserByDni(credentialDwelling.getBeneficiaryDni());

        if(didiAppUser.isPresent()) {
            credentialDwelling.setIdDidiReceptor(didiAppUser.get().getDid());
            credentialDwelling = credentialDwellingService.save(credentialDwelling);

            didiService.createAndEmmitCertificateDidi(credentialDwelling);

        }else{
            log.info("Id Didi for Beneficiary {} not exist, Credential Dwelling {} not emmited", credentialDwelling.getCreditHolderDni(), credentialDwelling.getId());
        }

    }


    public void emmitCredentialEntrepreneurship(CredentialEntrepreneurship credentialEntrepreneurship){

        log.info("Emmiting Credential Entrepreneurship id {} holder {} beneficiary {}",credentialEntrepreneurship.getId(), credentialEntrepreneurship.getCreditHolderDni(), credentialEntrepreneurship.getBeneficiaryDni());

        Optional<DidiAppUser> didiAppUser = this.didiAppUserService.getDidiAppUserByDni(credentialEntrepreneurship.getBeneficiaryDni());

        if(didiAppUser.isPresent()) {
            credentialEntrepreneurship.setIdDidiReceptor(didiAppUser.get().getDid());
            credentialEntrepreneurship = credentialEntrepreneurshipService.save(credentialEntrepreneurship);

            didiService.createAndEmmitCertificateDidi(credentialEntrepreneurship);

        }else{
            log.info("Id Didi for Beneficiary {} not exist, Credential Entrepreneurship {} not emmited", credentialEntrepreneurship.getCreditHolderDni(), credentialEntrepreneurship.getId());
        }

    }

    public void emmitCredentialBenefitSancor(CredentialBenefitSancor credentialBenefitSancor){

        log.info("Emmiting Credential Benefit Sancor id {} holder {} beneficiary {}",credentialBenefitSancor.getId(), credentialBenefitSancor.getCreditHolderDni(), credentialBenefitSancor.getBeneficiaryDni());

        Optional<DidiAppUser> didiAppUser = this.didiAppUserService.getDidiAppUserByDni(credentialBenefitSancor.getBeneficiaryDni());


        if(didiAppUser.isPresent()) {

            Optional<SancorPolicy>  opSancorPolicy = this.sancorPolicyService.findByCertificateClientDni(credentialBenefitSancor.getBeneficiaryDni());

            if(opSancorPolicy.isPresent()) {
                credentialBenefitSancor.setIdDidiReceptor(didiAppUser.get().getDid());

                credentialBenefitSancor.addPolicyData(opSancorPolicy.get());

                credentialBenefitSancor = credentialBenefitSancorService.save(credentialBenefitSancor);

                didiService.createAndEmmitCertificateDidi(credentialBenefitSancor);
            }else{
                log.info("Sancor policy for Beneficiary {} i not avaiable, Credential Benefit Sancor {} not emmited", credentialBenefitSancor.getCreditHolderDni(), credentialBenefitSancor.getId());

            }

        }else{
            log.info("Id Didi for Beneficiary {} not exist, Credential Benefit Sancor {} not emmited", credentialBenefitSancor.getCreditHolderDni(), credentialBenefitSancor.getId());
        }
    }

  public void processNewsAppDidiUsers(){

        List<DidiAppUser> didiAppUsersToProcces = didiAppUserService.getDidiAppUsersNeedProcess();

        if(didiAppUsersToProcces!=null && !didiAppUsersToProcces.isEmpty()){

            for(DidiAppUser didiAppUser : didiAppUsersToProcces) {
                try {
                    this.verifyCredentialIdentityForDidiAppUser(didiAppUser);
                    this.verifyCredentialCreditForDidiAppUser(didiAppUser);

                    didiAppUser.setSyncStatus(DidiSyncStatus.SYNC_OK.getCode());
                    didiAppUserService.save(didiAppUser);
                } catch (CredentialException e) {
                    log.error("Error updating info new id didi for dni {}",didiAppUser.getDni());
                }

            }

        }else{
            log.info("No new didi app user to process");
        }

    }

    //verify if credential identity is active and id didi is valid
    public void verifyCredentialIdentityForDidiAppUser(DidiAppUser didiAppUser) throws CredentialException {

        Optional<CredentialIdentity> credentialIdentityToVerify = this.credentialIdentityService.getCredentialIdentityActiveForDni(didiAppUser.getDni());
        if(credentialIdentityToVerify.isPresent()) {
            if(!credentialIdentityToVerify.get().getIdDidiReceptor().equals(didiAppUser.getDid())){
                credentialIdentityService.revokeComplete(credentialIdentityToVerify.get(), RevocationReasonsCodes.UPDATE_INTERNAL);
                CredentialIdentity newCredentialIdentity =  credentialIdentityService.buildNewOnPendidgDidi(credentialIdentityToVerify.get(), didiAppUser);
                credentialIdentityService.save(newCredentialIdentity);
                log.info("Credential Identity for dni {} updated to id didi {} and set on pending didi state",newCredentialIdentity.getBeneficiaryDni(), didiAppUser.getDid());
            }
            log.info("Credential Identity for dni {} dont need be updated",credentialIdentityToVerify.get().getBeneficiaryDni());
        }
        else
            log.info("Credential identity for dni {} is not present", didiAppUser.getDni());

    }

    //verify if credential identity is active and id didi is valid
    public void verifyCredentialCreditForDidiAppUser(DidiAppUser didiAppUser) throws CredentialException {

        List<CredentialCredit> credentialsCreditToVerify = this.credentialCreditService.getCredentialsCreditActiveForDni(didiAppUser.getDni());
        if(credentialsCreditToVerify!=null && !credentialsCreditToVerify.isEmpty()) {
            for(CredentialCredit credentialCredit : credentialsCreditToVerify) {
                if (!credentialCredit.getIdDidiReceptor().equals(didiAppUser.getDid())) {
                    this.credentialCreditService.revokeComplete(credentialCredit, RevocationReasonsCodes.UPDATE_INTERNAL);
                    CredentialCredit newCredentialCredit = credentialCreditService.buildNewOnPendidgDidi(credentialCredit, didiAppUser);
                    this.credentialCreditService.save(newCredentialCredit);
                    log.info("Credential Credit for dni {} and id credit {} updated to id didi {} and set on pending didi state", credentialCredit.getBeneficiaryDni(), credentialCredit.getIdBondareaCredit(), didiAppUser.getDid());
                }
                log.info("Credential Identity for dni {} and id credit {} dont need be updated", credentialCredit.getBeneficiaryDni(), credentialCredit.getIdBondareaCredit());
            }
        }
        else
            log.info("dni {} not have credentials credit active", didiAppUser.getDni());

    }


    /*
    private CredentialBenefitService credentialBenefitService;

    private CredentialDwellingService credentialDwellingService;

    private CredentialEntrepreneurshipService credentialEntrepreneurshipService;

    private CredentialBenefitSancorService credentialBenefitSancorService;*/
}
