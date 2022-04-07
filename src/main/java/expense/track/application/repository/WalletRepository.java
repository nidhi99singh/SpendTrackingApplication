package expense.track.application.repository;

import expense.track.application.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, String> {

  @Query("from Wallet where user_id=:userId")
  public Wallet getUserWallet(@Param("userId") String userId);

  @Query("select walletBalance from Wallet where user_id=:userId")
  public Integer getWalletBalance(@Param("userId") String userId);

  @Modifying
  @Transactional
  @Query("update Wallet set walletBalance=:walletBalance where user_id=:userId")
  public void updateWalletBalance(@Param("walletBalance") Integer walletBalance,@Param("userId") String userId);

}
